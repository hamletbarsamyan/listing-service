package am.jsl.listings.dao.category.mapper;

import am.jsl.listings.dao.DBUtils;
import am.jsl.listings.dto.category.CategoryAttributeLookupDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * {@link RowMapper} implementation that converts a row into a new CategoryAttributeLookupDTO instance.
 * @author hamlet
 */
public class CategoryAttributeLookupDTOMapper implements RowMapper<CategoryAttributeLookupDTO> {

	public CategoryAttributeLookupDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		CategoryAttributeLookupDTO attribute = new CategoryAttributeLookupDTO();
		attribute.setAttributeId(rs.getLong(DBUtils.attribute_id));
		attribute.setParentId(rs.getLong(DBUtils.parent_id));
		attribute.setAttrType(rs.getString(DBUtils.attr_type));
		attribute.setName(rs.getString(DBUtils.name));
		attribute.setExtraInfo(rs.getString(DBUtils.extra_info));
		return attribute;
	}
}
