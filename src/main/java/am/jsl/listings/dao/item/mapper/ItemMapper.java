package am.jsl.listings.dao.item.mapper;


import am.jsl.listings.dao.DBUtils;
import am.jsl.listings.domain.item.Item;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *{@link RowMapper} implementation that converts a row into a new Item instance.
 *
 * @author hamlet
 */
public class ItemMapper implements RowMapper<Item> {

	public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
		Item item = new Item();
		item.setId(rs.getLong(DBUtils.id));
		item.setCategoryId(rs.getLong(DBUtils.category_id));
		item.setListingType(rs.getByte(DBUtils.listing_type));

		item.setPrice(rs.getDouble(DBUtils.price));
		item.setCurrency(rs.getString(DBUtils.currency));
		item.setPurchasePrice(rs.getDouble(DBUtils.purchase_price));
		item.setPurchaseCurrency(rs.getString(DBUtils.purchase_currency));

		item.setInvCount(rs.getDouble(DBUtils.inv_count));
		item.setUpc(rs.getString(DBUtils.upc));
		item.setSku(rs.getString(DBUtils.sku));

		item.setActive(rs.getBoolean(DBUtils.active));

		item.setCreatedAt(rs.getTimestamp(DBUtils.created_at));
		item.setChangedAt(rs.getTimestamp(DBUtils.changed_at));
		item.setUserId(rs.getLong(DBUtils.user_id));
		return item;
	}
}
