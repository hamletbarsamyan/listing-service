package am.jsl.listings.dao.category.mapper;

import am.jsl.listings.dao.DBUtils;
import am.jsl.listings.domain.category.CategoryAttribute;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * {@link RowMapper} implementation that converts a row into a new CategoryAttribute instance.
 * @author hamlet
 */
public class CategoryAttributeMapper implements RowMapper<CategoryAttribute> {

	public CategoryAttribute mapRow(ResultSet rs, int rowNum) throws SQLException {
		CategoryAttribute attribute = new CategoryAttribute();
		attribute.setId(rs.getLong(DBUtils.id));
		attribute.setAttributeId(rs.getLong(DBUtils.attribute_id));
		attribute.setCategoryId(rs.getLong(DBUtils.category_id));
		attribute.setParentId(rs.getLong(DBUtils.parent_id));
		attribute.setSortOrder(rs.getInt(DBUtils.sort_order));
		attribute.setAttrType(rs.getString(DBUtils.attr_type));
		attribute.setName(rs.getString(DBUtils.name));
		attribute.setDescription(rs.getString(DBUtils.description));
		attribute.setExtraInfo(rs.getString(DBUtils.extra_info));
		attribute.setLocale(rs.getString(DBUtils.locale));
		return attribute;
	}
}
