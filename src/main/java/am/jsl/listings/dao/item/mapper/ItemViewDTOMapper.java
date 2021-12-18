package am.jsl.listings.dao.item.mapper;


import am.jsl.listings.dao.DBUtils;
import am.jsl.listings.dto.item.ItemViewDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *{@link RowMapper} implementation that converts a row into a new ItemViewDTO instance.
 *
 * @author hamlet
 */
public class ItemViewDTOMapper implements RowMapper<ItemViewDTO> {

	public ItemViewDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		ItemViewDTO item = new ItemViewDTO();
		item.setId(rs.getLong(DBUtils.id));
		item.setCategory(rs.getString(DBUtils.category));
		item.setCategoryId(rs.getLong(DBUtils.category_id));
		item.setListingType(rs.getByte(DBUtils.listing_type));

		item.setPrice(rs.getDouble(DBUtils.price));
		item.setCurrency(rs.getString(DBUtils.currency));
		item.setPurchasePrice(rs.getDouble(DBUtils.purchase_price));
		item.setPurchaseCurrency(rs.getString(DBUtils.purchase_currency));
		item.setActive(rs.getBoolean(DBUtils.active));

		item.setInvCount(rs.getDouble(DBUtils.inv_count));
		item.setUpc(rs.getString(DBUtils.upc));
		item.setSku(rs.getString(DBUtils.sku));

		item.setName(rs.getString(DBUtils.name));
		item.setDescription(rs.getString(DBUtils.description));

		item.setCreatedAt(rs.getDate(DBUtils.created_at));
		item.setChangedAt(rs.getDate(DBUtils.changed_at));
		item.setUserId(rs.getLong(DBUtils.user_id));
		return item;
	}
}
