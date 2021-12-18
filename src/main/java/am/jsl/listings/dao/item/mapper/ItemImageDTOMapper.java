package am.jsl.listings.dao.item.mapper;


import am.jsl.listings.dao.DBUtils;
import am.jsl.listings.domain.item.ItemImage;
import am.jsl.listings.dto.item.ItemImageDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *{@link RowMapper} implementation that converts a row into a new ItemImageDTO instance.
 *
 * @author hamlet
 */
public class ItemImageDTOMapper implements RowMapper<ItemImageDTO> {

	public ItemImageDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		ItemImageDTO itemImage = new ItemImageDTO();
		itemImage.setId(rs.getLong(DBUtils.id));
		itemImage.setFileName(rs.getString(DBUtils.file_name));
		itemImage.setSortOrder(rs.getInt(DBUtils.sort_order));
		return itemImage;
	}
}
