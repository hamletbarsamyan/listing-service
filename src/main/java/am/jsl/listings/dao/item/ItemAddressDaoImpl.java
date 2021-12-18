package am.jsl.listings.dao.item;


import am.jsl.listings.dao.DBUtils;
import am.jsl.listings.dao.MultiLingualDaoImpl;
import am.jsl.listings.dao.item.mapper.ItemAddressMapper;
import am.jsl.listings.domain.item.ItemAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * The implementation of Dao interface for accessing {@link ItemAddress} domain object.
 *
 * @author hamlet
 */
@Repository("itemAddressDao")
@Lazy
public class ItemAddressDaoImpl extends MultiLingualDaoImpl<ItemAddress> implements ItemAddressDao {
    private ItemAddressMapper itemAddressMapper = new ItemAddressMapper();

    @Autowired
    public ItemAddressDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    private static final String deleteSql = "delete from item_address where id = :id";

    @Override
    public void delete(long id) {
        delete(id, deleteSql);
    }

    private static final String createSql = "insert into item_address "
            + "(id, item_id, country_id, state_id, city_id, district_id, zip) "
            + "values(:id, :item_id, :country_id, :state_id, :city_id, :district_id, :zip)";

    @Override
    public void create(ItemAddress itemAddress) {
        long id = DBUtils.getNextId(getJdbcTemplate(), "item_address");
        itemAddress.setId(id);
        Map<String, Object> params = new HashMap<>(7);
        params.put(DBUtils.id, itemAddress.getId());
        params.put(DBUtils.item_id, itemAddress.getItemId());
        params.put(DBUtils.country_id, itemAddress.getCountryId());
        params.put(DBUtils.state_id, itemAddress.getStateId());
        params.put(DBUtils.city_id, itemAddress.getCityId());
        params.put(DBUtils.district_id, itemAddress.getDistrictId());
        params.put(DBUtils.zip, itemAddress.getZip());
        parameterJdbcTemplate.update(createSql, params);

        createTranslation(itemAddress);
    }

    private static final String updateSql = "update item_address "
            + "set country_id = :country_id, state_id = :state_id, city_id = :city_id, "
            + "district_id = :district_id, zip = :zip "
            + "where id = :id";

    @Override
    public void update(ItemAddress itemAddress) {
        Map<String, Object> params = new HashMap<>(6);
        params.put(DBUtils.id, itemAddress.getId());
        params.put(DBUtils.country_id, itemAddress.getCountryId());
        params.put(DBUtils.state_id, itemAddress.getStateId());
        params.put(DBUtils.city_id, itemAddress.getCityId());
        params.put(DBUtils.district_id, itemAddress.getDistrictId());
        params.put(DBUtils.zip, itemAddress.getZip());
        parameterJdbcTemplate.update(updateSql, params);

        int updatedRows = updateTranslation(itemAddress);

        if (updatedRows == 0) {
            createTranslation(itemAddress);
        }
    }

    private static final String createTrSql = "insert into item_address_tr "
            + "(item_address_id, address_line1, address_line2, locale) "
            + "values(:item_address_id, :address_line1, :address_line2, :locale)";

    public void createTranslation(ItemAddress item) {
        saveItemAddressTranslation(item, createTrSql);
    }

    private static final String updateTranslationSql = "update item_address_tr "
            + "set address_line1 = :address_line1, address_line2 = :address_line2 "
            + "where item_address_id = :item_address_id and locale = :locale";

    public int updateTranslation(ItemAddress item) {
        return saveItemAddressTranslation(item, updateTranslationSql);
    }

    /**
     * Saves the translation fields of the given item address.
     *
     * @param itemAddress the Descriptive
     * @param sql         the sql to execute
     * @return the updated row count
     */
    protected int saveItemAddressTranslation(ItemAddress itemAddress, String sql) {
        Map<String, Object> params = new HashMap<>(4);
        params.put(DBUtils.item_address_id, itemAddress.getId());
        params.put(DBUtils.address_line1, itemAddress.getAddressLine1());
        params.put(DBUtils.address_line2, itemAddress.getAddressLine2());
        params.put(DBUtils.locale, itemAddress.getLocale());
        return parameterJdbcTemplate.update(sql, params);
    }

    private static final String getSql = "select * from item_address i "
            + "inner join item_address_tr tr on tr.item_address_id = i.id and tr.locale = :locale "
            + "where i.id = :id";

    @Override
    public ItemAddress get(long id, String locale) {
        Map<String, Object> params = new HashMap<>(2);
        params.put(DBUtils.id, id);
        params.put(DBUtils.locale, locale);

        try {
            return parameterJdbcTemplate.queryForObject(getSql, params, itemAddressMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
