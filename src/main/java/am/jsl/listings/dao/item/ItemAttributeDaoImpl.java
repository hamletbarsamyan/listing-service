package am.jsl.listings.dao.item;


import am.jsl.listings.dao.BaseDaoImpl;
import am.jsl.listings.dao.DBUtils;
import am.jsl.listings.dao.item.mapper.ItemAttributeManageDTOMapper;
import am.jsl.listings.dao.item.mapper.ItemAttributeMapper;
import am.jsl.listings.dao.item.mapper.ItemAttributeViewDTOMapper;
import am.jsl.listings.domain.item.ItemAttribute;
import am.jsl.listings.dto.item.ItemAttributeManageDTO;
import am.jsl.listings.dto.item.ItemAttributeViewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The implementation of Dao interface for accessing {@link ItemAttribute} domain object.
 *
 * @author hamlet
 */
@Repository("itemAttributeDao")
@Lazy
public class ItemAttributeDaoImpl extends BaseDaoImpl<ItemAttribute> implements ItemAttributeDao {
    private ItemAttributeMapper attributeMapper = new ItemAttributeMapper();
    private ItemAttributeManageDTOMapper attributeManageDTOMapper = new ItemAttributeManageDTOMapper();
    private ItemAttributeViewDTOMapper attributeViewDTOMapper = new ItemAttributeViewDTOMapper();

    @Autowired
    public ItemAttributeDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    private static final String deleteItemAttributesSql = "delete from item_attribute where item_id = :item_id";
    private static final String createItemAttributesSql = "insert into item_attribute "
            + "(item_id, attribute_id, value) "
            + "values(?, ?, ?)";

    @Override
    public void saveItemAttributes(long itemId, List<ItemAttribute> attributes) {
        parameterJdbcTemplate.update(deleteItemAttributesSql, Collections.singletonMap(DBUtils.item_id, itemId));

        getJdbcTemplate().batchUpdate(createItemAttributesSql,
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i)
                            throws SQLException {
                        ItemAttribute itemAttribute = attributes.get(i);
                        ps.setLong(1, itemId);
                        ps.setLong(2, itemAttribute.getAttributeId());
                        ps.setString(3, itemAttribute.getValue());
                    }

                    public int getBatchSize() {
                        return attributes.size();
                    }
                });
    }

    private static final String getItemAttributeManageDTOsSql = "select a.id as attribute_id, a.attr_type, tr.name, tr.extra_info, ia.value "
            + "from item i "
            + "inner join category_attribute ca on ca.category_id = i.category_id "
            + "inner join attribute a on a.id = ca.attribute_id "
            + "left join attribute_tr tr on tr.attribute_id = a.id and tr.locale = :locale "
            + "left join item_attribute ia on ia.item_id = i.id and ia.attribute_id = a.id "
            + "where i.id = :item_id "
            + "order by ca.sort_order";

    @Override
    public List<ItemAttributeManageDTO> getItemAttributes(long itemId, String locale) {
        Map<String, Object> params = new HashMap<>(2);
        params.put(DBUtils.locale, locale);
        params.put(DBUtils.item_id, itemId);

        return parameterJdbcTemplate.query(getItemAttributeManageDTOsSql, params, attributeManageDTOMapper);
    }

    private static final String getItemAttributeViewDTOsSql = "select a.id, a.attr_type, tr.name, " +
            "IF(a.attr_type = 'LIST', IF(av.value != '', av.value, avtr.value_tr), ia.value) as value, tr.extra_info " +
            "from item i " +
            "inner join category_attribute ca on ca.category_id = i.category_id " +
            "inner join attribute a on a.id = ca.attribute_id " +
            "left join attribute_tr tr on tr.attribute_id = a.id and tr.locale = :locale " +
            "inner join item_attribute ia on ia.item_id = i.id and ia.attribute_id = a.id " +
            "left join attribute_value av on av.id = ia.value and a.attr_type = 'LIST'" +
            "left join attribute_value_tr avtr on avtr.attribute_value_id = av.id and avtr.locale = :locale " +
            "where i.id = :item_id " +
            "order by ca.sort_order";

    @Override
    public List<ItemAttributeViewDTO> getItemAttributeViewDTOs(long itemId, String locale) {
        Map<String, Object> params = new HashMap<>(2);
        params.put(DBUtils.locale, locale);
        params.put(DBUtils.item_id, itemId);

        return parameterJdbcTemplate.query(getItemAttributeViewDTOsSql, params, attributeViewDTOMapper);
    }
}
