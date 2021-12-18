package am.jsl.listings.dao.attribute;


import am.jsl.listings.dao.DBUtils;
import am.jsl.listings.dao.MultiLingualDaoImpl;
import am.jsl.listings.dao.attribute.mapper.AttributeByLocaleMapper;
import am.jsl.listings.dao.attribute.mapper.AttributeMapper;
import am.jsl.listings.dao.attribute.mapper.AttributeTranslationMapper;
import am.jsl.listings.domain.Translation;
import am.jsl.listings.domain.attribute.Attribute;
import am.jsl.listings.domain.attribute.AttributeTranslation;
import am.jsl.listings.domain.category.Category;
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
 * The implementation of Dao interface for accessing {@link Attribute} domain object.
 *
 * @author hamlet
 */
@Repository("attributeDao")
@Lazy
public class AttributeDaoImpl extends MultiLingualDaoImpl<Attribute> implements AttributeDao {
    private AttributeMapper attributeMapper = new AttributeMapper();
    private AttributeByLocaleMapper attributeByLocaleMapper = new AttributeByLocaleMapper();
    private AttributeTranslationMapper translationMapper = new AttributeTranslationMapper();

    private RowMapper<Attribute> lookupMapper = (rs, rowNum) -> {
        Attribute entity = new Attribute();
        entity.setId(rs.getLong(DBUtils.id));
        entity.setAttrType(rs.getString(DBUtils.attr_type));
        entity.setName(rs.getString(DBUtils.name));
        entity.setDescription(rs.getString(DBUtils.description));
        entity.setExtraInfo(rs.getString(DBUtils.extra_info));
        return entity;
    };

    @Autowired
    AttributeDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    private static final String canDeleteSql = "select a.id from attribute a " +
            "inner join item_attribute i on i.attribute_id = a.id " +
            "where a.id = :id";

    @Override
    public boolean canDelete(long id) {
        return canDelete(id, canDeleteSql);
    }

    private static final String deleteSql = "delete from attribute where id = :id";

    @Override
    public void delete(long id) {
        delete(id, deleteSql);
    }

    private static final String createSql = "insert into attribute "
            + "(id, attr_type) values(:id, :attr_type)";

    @Override
    public void create(Attribute attribute) {
        long id = DBUtils.getNextId(getJdbcTemplate(), "attribute");
        attribute.setId(id);
        Map<String, Object> params = new HashMap<>();
        params.put(DBUtils.id, attribute.getId());
        params.put(DBUtils.attr_type, attribute.getAttrType());
        parameterJdbcTemplate.update(createSql, params);
    }

    private static final String updateSql = "update attribute "
            + "set attr_type = :attr_type where id = :id";

    @Override
    public void update(Attribute attribute) {
        Map<String, Object> params = new HashMap<>();
        params.put(DBUtils.id, attribute.getId());
        params.put(DBUtils.attr_type, attribute.getAttrType());
        parameterJdbcTemplate.update(updateSql, params);
    }

    private static final String getByLocaleSql = "select * from attribute attr "
            + "left join attribute_tr tr on tr.attribute_id = attr.id and tr.locale = :locale "
            + "where attr.id = :id";

    @Override
    public Attribute get(long id, String locale) {
        return get(id, locale, getByLocaleSql, attributeByLocaleMapper);
    }

    private static final String getSql = "select * from attribute attr "
            + "where attr.id = :id";

    @Override
    public Attribute get(long id) {
        return get(id, getSql, attributeMapper);
    }

    private static final String lookupSql = "select a.id, a.attr_type, tr.name, tr.description, tr.extra_info from attribute a "
            + "left join attribute_tr tr on tr.attribute_id = a.id and tr.locale = :locale "
            + "order by tr.name";

    @Override
    public List<Attribute> lookup(String locale) {
        return parameterJdbcTemplate.query(lookupSql,
                Collections.singletonMap(DBUtils.locale, locale), lookupMapper);
    }

    private static final String listSql = "select * from attribute c "
            + "left join attribute_tr tr on tr.attribute_id = c.id and tr.locale = :locale "
            + "order by tr.name";

    public List<Attribute> list(String locale) {
        return list(locale, listSql, attributeByLocaleMapper);
    }

    private static final String getTranslationsSql = "select * from attribute_tr "
            + "where attribute_id = :attribute_id";

    @Override
    public List<AttributeTranslation> getTranslations(long attributeId) {
        try {
            return parameterJdbcTemplate.query(getTranslationsSql,
                    Collections.singletonMap(DBUtils.attribute_id, attributeId), translationMapper);
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    private static final String deleteTrSql = "delete from attribute_tr where attribute_id = :attribute_id";
    private static final String createTrSql = "insert into attribute_tr "
            + "(attribute_id, name, extra_info, description, locale) "
            + "values(?, ?, ?, ?, ?)";

    public void saveTranslations(final long attributeId, List<? extends Translation> translations) {
        parameterJdbcTemplate.update(deleteTrSql, Collections.singletonMap(DBUtils.attribute_id, attributeId));

        getJdbcTemplate().batchUpdate(createTrSql,
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i)
                            throws SQLException {

                        AttributeTranslation translation = (AttributeTranslation) translations.get(i);
                        ps.setLong(1, attributeId);
                        ps.setString(2, translation.getName());
                        ps.setString(3, translation.getExtraInfo());
                        ps.setString(4, translation.getDescription());
                        ps.setString(5, translation.getLocale());
                    }

                    public int getBatchSize() {
                        return translations.size();
                    }
                });
    }
}
