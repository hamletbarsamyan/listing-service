package am.jsl.listings.dao.category.mapper;

import am.jsl.listings.dao.DBUtils;
import am.jsl.listings.domain.category.CategoryAttribute;
import am.jsl.listings.dto.category.CategoryAttributeManageDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * {@link RowMapper} implementation that converts a row into a new CategoryAttributeManageDTO instance.
 * @author hamlet
 */
public class CategoryAttributeManageDTOMapper implements RowMapper<CategoryAttributeManageDTO> {

	public CategoryAttributeManageDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		CategoryAttributeManageDTO attribute = new CategoryAttributeManageDTO();
		attribute.setAttributeId(rs.getLong(DBUtils.attribute_id));
		attribute.setParentId(rs.getLong(DBUtils.parent_id));
		attribute.setParent(rs.getString(DBUtils.parent_name));
		attribute.setParentDescription(rs.getString(DBUtils.parent_description));
		attribute.setSortOrder(rs.getInt(DBUtils.sort_order));
		attribute.setAttrType(rs.getString(DBUtils.attr_type));
		attribute.setName(rs.getString(DBUtils.name));
		attribute.setDescription(rs.getString(DBUtils.description));
		return attribute;
	}
}
