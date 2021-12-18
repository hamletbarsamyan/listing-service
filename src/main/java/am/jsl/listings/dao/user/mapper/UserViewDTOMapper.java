package am.jsl.listings.dao.user.mapper;

import am.jsl.listings.dao.DBUtils;
import am.jsl.listings.dto.user.UserViewDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * {@link RowMapper} implementation that converts a row into a new UserViewDTO instance.
 * @author hamlet
 */
public class UserViewDTOMapper implements RowMapper<UserViewDTO> {

	public UserViewDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserViewDTO user = new UserViewDTO();
		user.setId(rs.getLong(DBUtils.id));
		user.setLogin(rs.getString(DBUtils.login));
		user.setFullName(rs.getString(DBUtils.full_name));
		user.setEmail(rs.getString(DBUtils.email));
		user.setIcon(rs.getString(DBUtils.icon));
		user.setLastLogin(rs.getTimestamp(DBUtils.last_login));
		user.setEnabled(rs.getBoolean(DBUtils.enabled));
		user.setRole(rs.getString(DBUtils.role));
		user.setZip(rs.getString(DBUtils.zip));
		user.setCreatedAt(rs.getTimestamp(DBUtils.created_at));
		return user;
	}
}
