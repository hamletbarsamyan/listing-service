package am.jsl.listings.dao.user.mapper;

import am.jsl.listings.dao.DBUtils;
import am.jsl.listings.domain.user.Role;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * {@link RowMapper} implementation that converts a row into a new Role instance.
 * @author hamlet
 */
public class RoleMapper implements RowMapper<Role> {

    @Override
    public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
        Role role = new Role();
        role.setId(rs.getLong(DBUtils.id));
        role.setDefaultRole(rs.getBoolean(DBUtils.default_role));
        role.setName(rs.getString(DBUtils.name));
        role.setDescription(rs.getString(DBUtils.description));
        return role;
    }
}

