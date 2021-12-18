package am.jsl.listings.dao.item.mapper;


import am.jsl.listings.dao.DBUtils;
import am.jsl.listings.domain.item.ItemImage;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *{@link RowMapper} implementation that converts a row into a new ItemImage instance.
 *
 * @author hamlet
 */
public class ItemImageMapper implements RowMapper<ItemImage> {

	public ItemImage mapRow(ResultSet rs, int rowNum) throws SQLException {
		ItemImage itemImage = new ItemImage();
		itemImage.setId(rs.getLong(DBUtils.id));
		itemImage.setItemId(rs.getLong(DBUtils.item_id));
		itemImage.setFileName(rs.getString(DBUtils.file_name));
		itemImage.setSortOrder(rs.getInt(DBUtils.sort_order));
		return itemImage;
	}
}
