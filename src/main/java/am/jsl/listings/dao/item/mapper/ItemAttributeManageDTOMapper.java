package am.jsl.listings.dao.item.mapper;


import am.jsl.listings.dao.DBUtils;
import am.jsl.listings.domain.item.ItemAttribute;
import am.jsl.listings.dto.item.ItemAttributeManageDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *{@link RowMapper} implementation that converts a row into a new ItemAttributeManageDTO instance.
 *
 * @author hamlet
 */
public class ItemAttributeManageDTOMapper implements RowMapper<ItemAttributeManageDTO> {

	public ItemAttributeManageDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		ItemAttributeManageDTO itemAttribute = new ItemAttributeManageDTO();
		itemAttribute.setAttributeId(rs.getLong(DBUtils.attribute_id));
		itemAttribute.setAttrType(rs.getString(DBUtils.attr_type));
		itemAttribute.setName(rs.getString(DBUtils.name));
		itemAttribute.setExtraInfo(rs.getString(DBUtils.extra_info));
		itemAttribute.setValue(rs.getString(DBUtils.value));
		return itemAttribute;
	}
}
