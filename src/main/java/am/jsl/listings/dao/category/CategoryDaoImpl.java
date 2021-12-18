package am.jsl.listings.dao.category;


import am.jsl.listings.dao.DBUtils;
import am.jsl.listings.dao.MultiLingualDaoImpl;
import am.jsl.listings.dao.attribute.mapper.AttributeValueByLocaleMapper;
import am.jsl.listings.dao.category.mapper.*;
import am.jsl.listings.domain.DescriptiveTranslation;
import am.jsl.listings.domain.Translation;
import am.jsl.listings.domain.attribute.AttributeValue;
import am.jsl.listings.domain.category.Category;
import am.jsl.listings.domain.category.CategoryAttribute;
import am.jsl.listings.dto.attribute.AttributeValueLookupDTO;
import am.jsl.listings.dto.category.CategoryAttributeLookupDTO;
import am.jsl.listings.dto.category.CategoryAttributeManageDTO;
import am.jsl.listings.dto.category.CategoryAttributeValueLookupDTO;
import am.jsl.listings.dto.category.CategoryTreeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The implementation of Dao interface for accessing {@link Category} domain object.
 *
 * @author hamlet
 */
@Repository("categoryDao")
@Lazy
public class CategoryDaoImpl extends MultiLingualDaoImpl<Category> implements CategoryDao {

    private CategoryMapper categoryMapper = new CategoryMapper();
    private CategoryByLocaleMapper categoryByLocaleMapper = new CategoryByLocaleMapper();
    private CategoryTranslationMapper translationMapper = new CategoryTranslationMapper();
    private CategoryAttributeMapper attributeMapper = new CategoryAttributeMapper();
    private CategoryAttributeManageDTOMapper categoryAttributeManageDTOMapper = new CategoryAttributeManageDTOMapper();
    private CategoryAttributeLookupDTOMapper attributeLookupDTOMapper = new CategoryAttributeLookupDTOMapper();
    private CategoryAttributeValuesExtractor attributeValuesExtractor = new CategoryAttributeValuesExtractor();
    private CategoryDTOMapper categoryDTOMapper = new CategoryDTOMapper();

    @Autowired
    private CategoryTreeExtractor categoryTreeExtractor;

    private RowMapper<Category> lookupMapper = (rs, rowNum) -> {
        Category entity = new Category();
        entity.setId(rs.getLong(DBUtils.id));
        entity.setParentId(rs.getLong(DBUtils.parent_id));
        entity.setName(rs.getString(DBUtils.name));
        return entity;
    };

    @Autowired
    CategoryDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    private static final String canDeleteSql = "select c.id from category c " +
            "where c.id = :id and ((c.id in (select distinct parent_id from category)) " +
            "or (c.id in (select distinct category_id from item)))";

    @Override
    public boolean canDelete(long id) {
        return canDelete(id, canDeleteSql);
    }

    private static final String deleteSql = "delete from category where id = :id";

    @Override
    public void delete(long id) {
        delete(id, deleteSql);
    }

    private static final String existsSql = "select id from category_tr " +
            "where LOWER(name) = :name and category_id != :id";

    @Override
    public boolean exists(String name, long id) {
        return exists(name, id, existsSql);
    }

    private static final String createSql = "insert into category "
            + "(id, slug, sort_order, parent_id) values(:id, :slug, :sort_order, :parent_id)";
    @Override
    public void create(Category category) {
        long id = DBUtils.getNextId(getJdbcTemplate(), "category");
        category.setId(id);
        Map<String, Object> params = new HashMap<>(4);
        params.put(DBUtils.id, category.getId());
        params.put(DBUtils.slug, category.getSlug());
        params.put(DBUtils.sort_order, category.getSortOrder());
        params.put(DBUtils.parent_id, category.getParentId());
        parameterJdbcTemplate.update(createSql, params);
    }
    private static final String updateSql = "update category "
            + "set slug = :slug, sort_order = :sort_order, parent_id = :parent_id "
            + "where id = :id";

    @Override
    public void update(Category category) {
        Map<String, Object> params = new HashMap<>(4);
        params.put(DBUtils.id, category.getId());
        params.put(DBUtils.slug, category.getSlug());
        params.put(DBUtils.sort_order, category.getSortOrder());
        params.put(DBUtils.parent_id, category.getParentId());
        parameterJdbcTemplate.update(updateSql, params);
    }

    private static final String getByLocaleSql = "select * from category c "
            + "left join category_tr tr on tr.category_id = c.id and tr.locale = :locale "
            + "where c.id = :id";

    @Override
    public Category get(long id, String locale) {
        return get(id, locale, getByLocaleSql, categoryByLocaleMapper);
    }

    private static final String getSql = "select * from category "
            + "where id = :id";
    @Override
    public Category get(long id) {
        return get(id, getSql, categoryMapper);
    }

    private static final String lookupByParentSql = "select c.id, tr.name from category c "
            + "left join category_tr tr on tr.category_id = c.id and tr.locale = :locale "
            + "where parent_id = :parent_id "
            + "order by c.sort_order";

    public List<Category> lookup(long parentId, String locale) {
        Map<String, Object> params = new HashMap<>(2);
        params.put(DBUtils.locale, locale);
        params.put(DBUtils.parent_id, parentId);

        return parameterJdbcTemplate.query(lookupByParentSql, params, lookupMapper);
    }

    private static final String lookupSql = "select c.id, c.parent_id, tr.name from category c "
            + "left join category_tr tr on tr.category_id = c.id and tr.locale = :locale "
            + "order by c.sort_order";

    @Override
    public List<Category> lookup(String locale) {
        return parameterJdbcTemplate.query(lookupSql,
                Collections.singletonMap(DBUtils.locale, locale), lookupMapper);
    }

    private static final String listSql = "select * from category c "
            + "left join category_tr tr on tr.category_id = c.id and tr.locale = :locale "
            + "order by tr.name";

    public List<Category> list(String locale)  {
        return list(locale, listSql, categoryMapper);
    }

    private static final String updateCategoryAttributeSql = "update category_attribute "
            + "set parent_id = :parent_id, sort_order = :sort_order "
            + "where category_id = :category_id and attribute_id = :attribute_id";

    private static final String createCategoryAttributeSql = "insert into category_attribute "
            + "(category_id, attribute_id, parent_id, sort_order) "
            + "values(:category_id, :attribute_id, :parent_id, :sort_order)";

    @Override
    public void saveOrUpdateCategoryAttribute(CategoryAttribute categoryAttribute) {
        Map<String, Object> params = new HashMap<>(4);
        params.put(DBUtils.category_id, categoryAttribute.getCategoryId());
        params.put(DBUtils.attribute_id, categoryAttribute.getAttributeId());
        params.put(DBUtils.sort_order, categoryAttribute.getSortOrder());
        params.put(DBUtils.parent_id, categoryAttribute.getParentId());
        int updatedRows = parameterJdbcTemplate.update(updateCategoryAttributeSql, params);

        if (updatedRows == 0) {
            parameterJdbcTemplate.update(createCategoryAttributeSql, params);
        }
    }

    private static final String deleteCategoryAttributeSql = "delete from category_attribute "
            + "where category_id = :category_id and attribute_id = :attribute_id";
    @Override
    public void deleteCategoryAttribute(CategoryAttribute categoryAttribute) {
        Map<String, Object> params = new HashMap<>();
        params.put(DBUtils.category_id, categoryAttribute.getCategoryId());
        params.put(DBUtils.attribute_id, categoryAttribute.getAttributeId());
        parameterJdbcTemplate.update(deleteCategoryAttributeSql, params);
    }

    private static final String updateIconSql = "update category set icon = :icon where id = :id";
    @Override
    public  void updateIcon(long categoryId, String icon) {
        Map<String, Object> params = new HashMap<>(2);
        params.put(DBUtils.id, categoryId);
        params.put(DBUtils.icon, icon);
        parameterJdbcTemplate.update(updateIconSql, params);
    }

    private static final String getCategoryTreeSql = "select * " +
            "from category c " +
            "left join category_tr tr on tr.category_id = c.id and tr.locale = :locale " +
            "order by c.parent_id, c.sort_order";
    @Override
    public List<CategoryTreeDTO> getCategoryTree(String locale) {
        return parameterJdbcTemplate.query(getCategoryTreeSql,
                Collections.singletonMap(DBUtils.locale, locale), categoryTreeExtractor);
    }

    private static final String getTranslationsSql = "select * from category_tr "
            + "where category_id = :category_id";

    @Override
    public List<DescriptiveTranslation> getTranslations(long categoryId) {
        try {
            return parameterJdbcTemplate.query(getTranslationsSql,
                    Collections.singletonMap(DBUtils.category_id, categoryId), translationMapper);
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    private static final String deleteTrSql = "delete from category_tr where category_id = :category_id";
    private static final String createTrSql = "insert into category_tr "
            + "(category_id, name, description, locale) "
            + "values(?, ?, ?, ?)";

    public void saveTranslations(final long categoryId, List<? extends Translation> translations) {
        parameterJdbcTemplate.update(deleteTrSql, Collections.singletonMap(DBUtils.category_id, categoryId));

        getJdbcTemplate().batchUpdate(createTrSql,
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i)
                            throws SQLException {

                        DescriptiveTranslation translation = (DescriptiveTranslation) translations.get(i);
                        ps.setLong(1, categoryId);
                        ps.setString(2, translation.getName());
                        ps.setString(3, translation.getDescription());
                        ps.setString(4, translation.getLocale());
                    }

                    public int getBatchSize() {
                        return translations.size();
                    }
                });
    }

    private static final String getCategoryTreeNamesSql = "select tr.name " +
            "from category c " +
            "left join category_tr tr on tr.category_id = c.id and tr.locale = :locale " +
            "where c.id in (select parent_id from category_tree where category_id = :category_id) " +
            "order by c.parent_id, c.sort_order";
    @Override
    public List<String> getCategoryTreeNames(long id, String locale) {
        Map<String, Object> params = new HashMap<>(2);
        params.put(DBUtils.category_id, id);
        params.put(DBUtils.locale, locale);

        try {
            return parameterJdbcTemplate.queryForList(getCategoryTreeNamesSql, params, String.class);
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    private static final String getCategoryTreeIdsSql = "select c.id " +
            "from category c " +
            "where c.id in (select parent_id from category_tree where category_id = :category_id) " +
            "order by c.parent_id, c.sort_order";
    @Override
    public List<Long> getCategoryTree(long id) {
        try {
            return parameterJdbcTemplate.queryForList(getCategoryTreeIdsSql,
                    Collections.singletonMap(DBUtils.category_id, id), Long.class);
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    private static final String deleteCategoryTreeSql = "delete from category_tree where category_id = :category_id";
    private static final String createCategoryTreeSql = "insert into category_tree "
            + "(category_id, parent_id) "
            + "values(?, ?)";

    @Override
    public void saveCategoryTree(long categoryId, List<Long> parentIds) {
        parameterJdbcTemplate.update(deleteCategoryTreeSql, Collections.singletonMap(DBUtils.category_id, categoryId));

        getJdbcTemplate().batchUpdate(createCategoryTreeSql,
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i)
                            throws SQLException {
                        ps.setLong(1, categoryId);
                        ps.setLong(2, parentIds.get(i));
                    }

                    public int getBatchSize() {
                        return parentIds.size();
                    }
                });
    }

    private static final String getCategoryAttributesSql = "select * from attribute a "
            + "inner join category_attribute ca on ca.attribute_id = a.id "
            + "left join attribute_tr tr on tr.attribute_id = a.id and tr.locale = :locale "
            + "where ca.category_id = :category_id "
            + "order by ca.sort_order";
    @Override
    public List<CategoryAttribute> getCategoryAttributes(long categoryId, String locale) {
        Map<String, Object> params = new HashMap<>(2);
        params.put(DBUtils.locale, locale);
        params.put(DBUtils.category_id, categoryId);

        return parameterJdbcTemplate.query(getCategoryAttributesSql, params, attributeMapper);
    }

    private static final String getCategoryAttributeManageDTOsSql = "select ca.attribute_id, ca.parent_id, ca.sort_order, "
            + "parent_tr.name as parent_name, parent_tr.description as parent_description, a.attr_type, tr.name, tr.description "
            + "from attribute a "
            + "inner join category_attribute ca on ca.attribute_id = a.id "
            + "left join attribute_tr tr on tr.attribute_id = a.id and tr.locale = :locale "
            + "left join attribute_tr parent_tr on parent_tr.attribute_id = ca.parent_id  and parent_tr.locale = :locale "
            + "where ca.category_id = :category_id "
            + "order by ca.sort_order";
    @Override
    public List<CategoryAttributeManageDTO> getCategoryAttributeManageDTOs(long categoryId, String locale) {
        Map<String, Object> params = new HashMap<>(2);
        params.put(DBUtils.locale, locale);
        params.put(DBUtils.category_id, categoryId);

        return parameterJdbcTemplate.query(getCategoryAttributeManageDTOsSql, params,
                categoryAttributeManageDTOMapper);
    }

    private static final String deleteCategoryAttributesSql = "delete from category_attribute where category_id = :category_id";
    private static final String createCategoryAttributesSql = "insert into category_attribute "
            + "(category_id, attribute_id, parent_id, sort_order) "
            + "values(?, ?, ?, ?)";
    @Override
    public void saveCategoryAttributes(final long categoryId, final List<CategoryAttributeManageDTO> attributes) {
        parameterJdbcTemplate.update(deleteCategoryAttributesSql, Collections.singletonMap(DBUtils.category_id, categoryId));

        getJdbcTemplate().batchUpdate(createCategoryAttributesSql,
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i)
                            throws SQLException {
                        CategoryAttributeManageDTO manageDTO = attributes.get(i);
                        ps.setLong(1, categoryId);
                        ps.setLong(2, manageDTO.getAttributeId());
                        ps.setLong(3, manageDTO.getParentId());
                        ps.setLong(4, manageDTO.getSortOrder());
                    }

                    public int getBatchSize() {
                        return attributes.size();
                    }
                });
    }

    private static final String lookupAttributesSql = "select a.id as attribute_id, ca.parent_id, " +
            "a.attr_type, tr.name, tr.extra_info "
            + "from attribute a "
            + "inner join category_attribute ca on ca.attribute_id = a.id "
            + "left join attribute_tr tr on tr.attribute_id = a.id and tr.locale = :locale "
            + "where ca.category_id = :category_id "
            + "order by ca.sort_order";
    @Override
    public List<CategoryAttributeLookupDTO> lookupAttributes(long categoryId, String locale) {
        Map<String, Object> params = new HashMap<>(2);
        params.put(DBUtils.locale, locale);
        params.put(DBUtils.category_id, categoryId);

        return parameterJdbcTemplate.query(lookupAttributesSql, params, attributeLookupDTOMapper);
    }

    private static final String lookupAttributeValuesSql = "select av.id, av.attribute_id, " +
            "IF(av.value != '', av.value, avtr.value_tr) AS value "
            + "from attribute a "
            + "inner join category_attribute ca on ca.attribute_id = a.id "
            + "inner join attribute_value av on av.attribute_id = a.id "
            + "left join attribute_tr tr on tr.attribute_id = a.id and tr.locale = :locale "
            + "left join attribute_value_tr avtr on avtr.attribute_value_id = av.id and avtr.locale = :locale "
            + "where ca.category_id = :category_id "
            + "order by ca.sort_order";
    @Override
    public Map<Long, List<CategoryAttributeValueLookupDTO>> lookupAttributeValues(long categoryId, String locale) {
        Map<String, Object> params = new HashMap<>(2);
        params.put(DBUtils.locale, locale);
        params.put(DBUtils.category_id, categoryId);

        return parameterJdbcTemplate.query(lookupAttributeValuesSql, params, attributeValuesExtractor);
    }

    private static final String getCategoriesCountql = "select count(id) from category";
    @Override
    public int getCategoriesCount() {
        return getJdbcTemplate().queryForObject(getCategoriesCountql, Integer.class);
    }
}
