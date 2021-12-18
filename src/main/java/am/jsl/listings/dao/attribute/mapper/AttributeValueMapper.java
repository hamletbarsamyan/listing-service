package am.jsl.listings.dao.attribute.mapper;

import am.jsl.listings.dao.DBUtils;
import am.jsl.listings.domain.attribute.AttributeValue;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * {@link RowMapper} implementation that converts a row into a new AttributeValue instance.
 * @author hamlet
 */
public class AttributeValueMapper implements RowMapper<AttributeValue> {

	public AttributeValue mapRow(ResultSet rs, int rowNum) throws SQLException {
		AttributeValue attributeValue = new AttributeValue();
		attributeValue.setId(rs.getLong(DBUtils.id));
		attributeValue.setAttributeId(rs.getLong(DBUtils.attribute_id));
		attributeValue.setParentValueId(rs.getLong(DBUtils.parent_value_id));
		attributeValue.setValue(rs.getString(DBUtils.value));
		attributeValue.setSortOrder(rs.getInt(DBUtils.sort_order));
		return attributeValue;
	}
}
