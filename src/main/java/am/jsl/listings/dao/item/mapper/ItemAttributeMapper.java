package am.jsl.listings.dao.item.mapper;


import am.jsl.listings.dao.DBUtils;
import am.jsl.listings.domain.item.ItemAttribute;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *{@link RowMapper} implementation that converts a row into a new ItemAttribute instance.
 *
 * @author hamlet
 */
public class ItemAttributeMapper implements RowMapper<ItemAttribute> {

	public ItemAttribute mapRow(ResultSet rs, int rowNum) throws SQLException {
		ItemAttribute itemAttribute = new ItemAttribute();
		itemAttribute.setItemId(rs.getLong(DBUtils.item_id));
		itemAttribute.setAttributeId(rs.getLong(DBUtils.attribute_id));
		itemAttribute.setValue(rs.getString(DBUtils.value));
		return itemAttribute;
	}
}
