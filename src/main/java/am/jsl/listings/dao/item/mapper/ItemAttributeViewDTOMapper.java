package am.jsl.listings.dao.item.mapper;


import am.jsl.listings.dao.DBUtils;
import am.jsl.listings.dto.item.ItemAttributeViewDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *{@link RowMapper} implementation that converts a row into a new ItemAttributeViewDTO instance.
 *
 * @author hamlet
 */
public class ItemAttributeViewDTOMapper implements RowMapper<ItemAttributeViewDTO> {

	public ItemAttributeViewDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		ItemAttributeViewDTO itemAttribute = new ItemAttributeViewDTO();
		itemAttribute.setId(rs.getLong(DBUtils.id));
		itemAttribute.setAttributeType(rs.getString(DBUtils.attr_type));
		itemAttribute.setName(rs.getString(DBUtils.name));
		itemAttribute.setValue(rs.getString(DBUtils.value));
		itemAttribute.setExtraInfo(rs.getString(DBUtils.extra_info));
		return itemAttribute;
	}
}
