package am.jsl.listings.dao.item.mapper;


import am.jsl.listings.dao.DBUtils;
import am.jsl.listings.domain.item.Item;
import am.jsl.listings.domain.item.ItemAddress;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *{@link RowMapper} implementation that converts a row into a new ItemAddress instance.
 *
 * @author hamlet
 */
public class ItemAddressMapper implements RowMapper<ItemAddress> {

	public ItemAddress mapRow(ResultSet rs, int rowNum) throws SQLException {
		ItemAddress itemAddress = new ItemAddress();
		itemAddress.setId(rs.getLong(DBUtils.id));
		itemAddress.setItemId(rs.getLong(DBUtils.item_id));

		itemAddress.setCountryId(rs.getLong(DBUtils.country_id));
		itemAddress.setCityId(rs.getLong(DBUtils.city_id));
		itemAddress.setStateId(rs.getLong(DBUtils.state_id));
		itemAddress.setDistrictId(rs.getLong(DBUtils.district_id));

		itemAddress.setAddressLine1(rs.getString(DBUtils.address_line1));
		itemAddress.setAddressLine2(rs.getString(DBUtils.address_line2));
		itemAddress.setLocale(rs.getString(DBUtils.locale));
		return itemAddress;
	}
}
