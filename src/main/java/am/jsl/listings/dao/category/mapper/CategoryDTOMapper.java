package am.jsl.listings.dao.category.mapper;

import am.jsl.listings.dao.DBUtils;
import am.jsl.listings.dto.category.CategoryDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * {@link RowMapper} implementation that converts a row into a new CategoryDTO instance.
 *
 * @author hamlet
 */
public class CategoryDTOMapper implements RowMapper<CategoryDTO> {

	public CategoryDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		CategoryDTO category = new CategoryDTO();
		category.setId(rs.getLong(DBUtils.id));
		category.setName(rs.getString(DBUtils.name));
		category.setSlug(rs.getString(DBUtils.slug));
		category.setIcon(rs.getString(DBUtils.icon));
		category.setDescription(rs.getString(DBUtils.description));
		category.setSortOrder(rs.getInt(DBUtils.sort_order));
		category.setParentId(rs.getLong(DBUtils.parent_id));
		return category;
	}
}
