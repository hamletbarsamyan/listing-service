package am.jsl.listings.dao.user.mapper;

import am.jsl.listings.dao.DBUtils;
import am.jsl.listings.domain.user.Permission;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * {@link RowMapper} implementation that converts a row into a new Permission instance.
 * @author hamlet
 */
public class PermissionMapper implements RowMapper {

    @Override
    public Permission mapRow(ResultSet rs, int rowNum) throws SQLException {
        Permission dto = new Permission();
        dto.setId(rs.getLong(DBUtils.id));
        dto.setName(rs.getString(DBUtils.name));
        dto.setDescription(rs.getString(DBUtils.description));
        return dto;
    }
}