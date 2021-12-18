package am.jsl.listings.dao.user.mapper;

import am.jsl.listings.dao.DBUtils;
import am.jsl.listings.domain.user.User;
import am.jsl.listings.dto.user.UserListDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * {@link RowMapper} implementation that converts a row into a new UserListDTO instance.
 * @author hamlet
 */
public class UserListDTOMapper implements RowMapper<UserListDTO> {

	public UserListDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserListDTO user = new UserListDTO();
		user.setId(rs.getLong(DBUtils.id));
		user.setLogin(rs.getString(DBUtils.login));
		user.setFullName(rs.getString(DBUtils.full_name));
		user.setEmail(rs.getString(DBUtils.email));
		user.setEnabled(rs.getBoolean(DBUtils.enabled));
		user.setLastLogin(rs.getTimestamp(DBUtils.last_login));
		return user;
	}
}
