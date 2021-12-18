package am.jsl.listings.dao.attribute;


import am.jsl.listings.dao.DBUtils;
import am.jsl.listings.dao.MultiLingualDaoImpl;
import am.jsl.listings.dao.attribute.mapper.AttributeValueByLocaleMapper;
import am.jsl.listings.dao.attribute.mapper.AttributeValueMapper;
import am.jsl.listings.dao.attribute.mapper.AttributeValueTranslationMapper;
import am.jsl.listings.domain.attribute.AttributeValue;
import am.jsl.listings.domain.attribute.AttributeValueTranslation;
import am.jsl.listings.dto.attribute.AttributeValueLookupDTO;
import am.jsl.listings.dto.attribute.AttributeValueManageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The implementation of Dao interface for accessing {@link AttributeValue} domain object.
 *
 * @author hamlet
 */
@Repository("attributeValueDao")
@Lazy
public class AttributeValueDaoImpl extends MultiLingualDaoImpl<AttributeValue> implements AttributeValueDao {
    private AttributeValueMapper attributeValueMapper = new AttributeValueMapper();
    private AttributeValueByLocaleMapper attributeValueByLocaleMapper = new AttributeValueByLocaleMapper();
    private AttributeValueTranslationMapper translationMapper = new AttributeValueTranslationMapper();

    private RowMapper<AttributeValue> lookupMapper = (rs, rowNum) -> {
        AttributeValue entity = new AttributeValue();
        entity.setId(rs.getLong(DBUtils.id));
        entity.setValue(rs.getString(DBUtils.value));
        entity.setValueTr(rs.getString(DBUtils.value_tr));
        return entity;
    };

    @Autowired
    AttributeValueDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    private static final String deleteSql = "delete from attribute_value where id = :id";

    @Override
    public void delete(long id) {
        delete(id, deleteSql);
    }

    private static final String createSql = "insert into attribute_value "
            + "(id, attribute_id, parent_value_id, value, sort_order) values(:id, :attribute_id, :parent_value_id, :value, :sort_order)";
    @Override
    public void create(AttributeValue attributeValue) {
        long id = DBUtils.getNextId(getJdbcTemplate(), "attribute_value");
        attributeValue.setId(id);
        Map<String, Object> params = new HashMap<>(5);
        params.put(DBUtils.id, attributeValue.getId());
        params.put(DBUtils.attribute_id, attributeValue.getAttributeId());
        params.put(DBUtils.parent_value_id, attributeValue.getParentValueId());
        params.put(DBUtils.value, attributeValue.getValue());
        params.put(DBUtils.sort_order, attributeValue.getSortOrder());
        parameterJdbcTemplate.update(createSql, params);
    }

    private static final String updateSql = "update attribute_value parent_value_id = :parent_value_id,"
            + "set value = :value, sort_order = :sort_order where id = :id";

    @Override
    public void update(AttributeValue attributeValue) {
        Map<String, Object> params = new HashMap<>(4);
        params.put(DBUtils.id, attributeValue.getId());
        params.put(DBUtils.parent_value_id, attributeValue.getParentValueId());
        params.put(DBUtils.value, attributeValue.getValue());
        params.put(DBUtils.sort_order, attributeValue.getSortOrder());
        parameterJdbcTemplate.update(updateSql, params);
    }

    private static final String getSql = "select * from attribute_value v "
            + "left join attribute_value_tr tr on tr.attribute_value_id = v.id and tr.locale = :locale "
            + "where v.id = :id";

    @Override
    public AttributeValue get(long id, String locale) {
        return get(id, locale, getSql, attributeValueMapper);
    }

    private static final String lookupSql = "select * from attribute_value v "
            + "left join attribute_value_tr tr on tr.attribute_value_id = v.id and tr.locale = :locale "
            + "where v.attribute_id = :attribute_id "
            + "order by v.sort_order, tr.value_tr";

    @Override
    public List<AttributeValue> lookup(long attributeId, String locale) {
        Map<String, Object> params = new HashMap<>();
        params.put(DBUtils.locale, locale);
        params.put(DBUtils.attribute_id, attributeId);

        return parameterJdbcTemplate.query(lookupSql, params, lookupMapper);
    }

    private static final String getAttributeValuesByLocaleSql = "select * from attribute_value v "
            + "left join attribute_value_tr tr on tr.attribute_value_id = v.id and tr.locale = :locale "
            + "where v.attribute_id = :attribute_id "
            + "order by v.sort_order, tr.value_tr";
    @Override
    public List<AttributeValue> getAttributeValues(long attributeId, String locale) {
        Map<String, Object> params = new HashMap<>();
        params.put(DBUtils.attribute_id, attributeId);
        params.put(DBUtils.locale, locale);

        return parameterJdbcTemplate.query(getAttributeValuesByLocaleSql, params, attributeValueByLocaleMapper);
    }

    private static final String getAttributeValuesSql = "select * from attribute_value v "
            + "where v.attribute_id = :attribute_id "
            + "order by v.sort_order, v.value";
    @Override
    public List<AttributeValue> getAttributeValues(long attributeId) {
        return parameterJdbcTemplate.query(getAttributeValuesSql,
                Collections.singletonMap(DBUtils.attribute_id, attributeId), attributeValueMapper);
    }

    private static final String deleteAttributeValuesSql = "delete from attribute_value where attribute_id = :attribute_id";
    @Override
    public void deleteAttributeValues(long attributeId) {
        parameterJdbcTemplate.update(deleteAttributeValuesSql,
                Collections.singletonMap(DBUtils.attribute_id, attributeId));
    }

    private static final String createTrSql = "insert into attribute_value_tr "
            + "(attribute_value_id, value_tr, locale) "
            + "values(:attribute_value_id, :value_tr, :locale)";

    @Override
    public void saveAttributeValues(final long attributeId, final List<AttributeValueManageDTO> attributeValues) {
        for (AttributeValueManageDTO attributeValueManageDTO : attributeValues) {
            // create attribute value
            AttributeValue attributeValue = attributeValueManageDTO.toAttributeValue();
            attributeValue.setAttributeId(attributeId);
            create(attributeValue);

            // create translations
            long attributeValueId = attributeValue.getId();
            List<AttributeValueTranslation> translations = attributeValueManageDTO.getTranslations();

            for (AttributeValueTranslation translation : translations) {
                String valueTr = translation.getValueTr();

                if (StringUtils.hasText(valueTr)) {
                    Map<String, Object> params = new HashMap<>(3);
                    params.put(DBUtils.attribute_value_id, attributeValueId);
                    params.put(DBUtils.value_tr, valueTr);
                    params.put(DBUtils.locale, translation.getLocale());
                    parameterJdbcTemplate.update(createTrSql, params);
                }
            }
        }
    }

    private static final String getTranslationsSql = "select * from attribute_value_tr "
            + "where attribute_value_id = :attribute_value_id order by locale";

    @Override
    public List<AttributeValueTranslation> getTranslations(long attributeValueId) {
        try {
            return parameterJdbcTemplate.query(getTranslationsSql,
                    Collections.singletonMap(DBUtils.attribute_value_id, attributeValueId), translationMapper);
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<AttributeValueLookupDTO> lookupAttributeValues(long attributeId, String locale) {
        return null;
    }
}
