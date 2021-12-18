package am.jsl.listings.dao.user.mapper;

import am.jsl.listings.dao.DBUtils;
import am.jsl.listings.domain.user.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * {@link RowMapper} implementation that converts a row into a new User instance.
 * @author hamlet
 */
public class UserMapper implements RowMapper<User> {

	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		user.setId(rs.getLong(DBUtils.id));
		user.setLogin(rs.getString(DBUtils.login));
		user.setFullName(rs.getString(DBUtils.full_name));
		user.setEmail(rs.getString(DBUtils.email));
		user.setIcon(rs.getString(DBUtils.icon));
		user.setLastLogin(rs.getTimestamp(DBUtils.last_login));
		user.setEnabled(rs.getBoolean(DBUtils.enabled));
		user.setRoleId(rs.getLong(DBUtils.role_id));
		user.setZip(rs.getString(DBUtils.zip));
		user.setCreatedAt(rs.getTimestamp(DBUtils.created_at));
		return user;
	}
}
