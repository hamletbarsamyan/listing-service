package am.jsl.listings.dao.user;

import am.jsl.listings.dao.BaseDaoImpl;
import am.jsl.listings.dao.DBUtils;
import am.jsl.listings.dao.user.mapper.*;
import am.jsl.listings.domain.user.User;
import am.jsl.listings.domain.user.VerificationToken;
import am.jsl.listings.domain.user.VerificationTokenType;
import am.jsl.listings.dto.user.UserListDTO;
import am.jsl.listings.dto.user.UserProfileDTO;
import am.jsl.listings.dto.user.UserViewDTO;
import am.jsl.listings.ex.UserNotFoundException;
import am.jsl.listings.search.ListPaginatedResult;
import am.jsl.listings.search.user.UserSearchQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.*;

/**
 * The implementation of Dao interface for accessing {@link User} domain object.
 * @author hamlet
 */
@Repository("userDao")
@Lazy
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {
    private VerificationTokenMapper tokenMapper = new VerificationTokenMapper();
    private UserMapper userMapper = new UserMapper();
    private UserListDTOMapper userListDTOMapper = new UserListDTOMapper();
    private UserViewDTOMapper userViewDTOMapper = new UserViewDTOMapper();
    private UserProfileDTOMapper userProfileDTOMapper = new UserProfileDTOMapper();

    private RowMapper<User> userLoginMapper = (rs, rowNum) -> {
        User user = new User();
        user.setId(rs.getLong(DBUtils.id));
        user.setLogin(rs.getString(DBUtils.login));
        user.setPassword(rs.getString(DBUtils.password));
        user.setFullName(rs.getString(DBUtils.full_name));
        user.setEmail(rs.getString(DBUtils.email));
        user.setIcon(rs.getString(DBUtils.icon));
        user.setEnabled(rs.getBoolean(DBUtils.enabled));
        user.setRoleId(rs.getLong(DBUtils.role_id));
        user.setZip(rs.getString(DBUtils.zip));

        return user;
    };

    @Autowired
    @Qualifier("permissionDao")
    private PermissionDao permissionDao;

    @Autowired
    UserDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    private static final String loginSql = "update t_user set last_login = :last_login where id = :id";

    @Override
    public void login(long userId) {
        Map<String, Object> params = new HashMap<>(2);
        params.put(DBUtils.id, userId);
        params.put(DBUtils.last_login,
                new Timestamp(System.currentTimeMillis()));
        parameterJdbcTemplate.update(loginSql, params);
    }

    private static final String canDeleteSql = "select i.id from item i " +
            "where i.user_id = :id";

    @Override
    public boolean canDelete(long id) {
        return canDelete(id, canDeleteSql);
    }

    private static final String deleteSql = "delete from t_user where id = :id";

    @Override
    public void delete(long id) {
        parameterJdbcTemplate.update(deleteSql, Collections.singletonMap(DBUtils.id, id));
    }

    private static final String loginExistsSql = "select id from t_user where LOWER(login) = ? and id != ?";

    @Override
    public boolean loginExists(String login, long id) {
        List<Long> list = getJdbcTemplate().queryForList(loginExistsSql,
                new Object[]{login.toLowerCase(), id}, Long.class);
        return list.size() > 0;
    }

    private static final String emailExistsSql = "select id from t_user where email = ? and id != ?";

    @Override
    public boolean emailExists(String email, long id) {
        List<Long> list = getJdbcTemplate().queryForList(emailExistsSql,
                new Object[]{email, id}, Long.class);
        return list.size() > 0;
    }

    private static final String getUsersSql = "select * from t_user order by login";
    @Override
    public List<User> list() {
        return getJdbcTemplate().query(getUsersSql, userMapper);
    }

    private static final String createUserSql = "insert into t_user "
            + "(id, login, password, full_name, email, zip, "
            + "icon, enabled, role_id, created_at, changed_by, changed_at) "
            + "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    @Override
    public void create(User user) {
        long userId = DBUtils.getNextId(getJdbcTemplate(), "t_user");
        user.setId(userId);
        getJdbcTemplate().update(
                createUserSql,
                new Object[] { user.getId(), user.getLogin(),
                        user.getPassword(), user.getFullName(),
                        user.getEmail(), user.getZip(),  user.getIcon(), user.isEnabled(),
                        user.getRoleId(), user.getCreatedAt(),
                        user.getChangedBy(), user.getChangedAt()});
    }

    private static final String updateUserSql = "update t_user "
            + "set login = :login, full_name = :full_name, email = :email, "
            + "email = :email, zip = :zip, icon = :icon, enabled = :enabled, role_id = :role_id, " +
            "changed_by = :changed_by, changed_at = :changed_at where id = :id";

    @Override
    public void update(User user) {
        Map<String, Object> params = new HashMap<>();
        params.put(DBUtils.id, user.getId());
        params.put(DBUtils.login, user.getLogin());
        params.put(DBUtils.full_name, user.getFullName());
        params.put(DBUtils.email, user.getEmail());
        params.put(DBUtils.zip, user.getZip());
        params.put(DBUtils.icon, user.getIcon());
        params.put(DBUtils.enabled, user.isEnabled());
        params.put(DBUtils.role_id, user.getRoleId());
        params.put(DBUtils.changed_by, user.getChangedBy());
        params.put(DBUtils.changed_at, user.getChangedAt());
        parameterJdbcTemplate.update(updateUserSql, params);
    }

    private static final String getUserSql = "select * from t_user u where u.id = :id";
    @Override
    public User get(long id) {
        User user = null;

        try {
            user = parameterJdbcTemplate.queryForObject(
                    getUserSql, Collections.singletonMap(DBUtils.id, id), userMapper);
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException();
        }

        user.setPermissions(permissionDao.getRolePermissions(user.getRoleId()));
        return user;
    }

    private static final String changePasswordSql = "update t_user set changed_by = :changed_by, "
            + "changed_at = :changed_at, password = :password where id = :id";

    @Override
    public void changePassword(String encryptedPassword, long userId) {
        Map<String, Object> params = new HashMap<>(5);
        params.put(DBUtils.id, userId);
        params.put(DBUtils.password, encryptedPassword);
        params.put(DBUtils.changed_by, userId);
        params.put(DBUtils.changed_at,
                new Timestamp(System.currentTimeMillis()));
        params.put(DBUtils.last_password_change, new Date());
        parameterJdbcTemplate.update(changePasswordSql, params);
    }

    private static final String getUserByNameSql = "select * from t_user u where u.login = :login";

    @Override
    public User getUser(String username) {
        User user = null;

        try {
            user = parameterJdbcTemplate.queryForObject(getUserByNameSql,
                    Collections.singletonMap(DBUtils.login, username), userLoginMapper);
        } catch (EmptyResultDataAccessException e) {
            // skip
        }

        if (user == null) {
            throw new UserNotFoundException("User [" + username + "] not found");
        }

        user.setPermissions(permissionDao.getRolePermissions(user.getRoleId()));

        return user;
    }

    @Override
    public UserProfileDTO getUserProfile(String username) {
        UserProfileDTO user = null;

        try {
            user = parameterJdbcTemplate.queryForObject(getUserByNameSql,
                    Collections.singletonMap(DBUtils.login, username), userProfileDTOMapper);
        } catch (EmptyResultDataAccessException e) {
            // skip
        }

        if (user == null) {
            throw new UserNotFoundException("User [" + username + "] not found");
        }

        return user;
    }

    private static final String updateIconSql = "update t_user set icon = :icon where id = :id";
    @Override
    public void updateIcon(User user) {
        Map<String, Object> params = new HashMap<>(2);
        params.put(DBUtils.id, user.getId());
        params.put(DBUtils.icon, user.getIcon());
        parameterJdbcTemplate.update(updateIconSql, params);
    }

    private static final String updateProfileSql = "update t_user "
            + "set login = :login, full_name = :full_name, email = :email, zip = :zip, " +
            "changed_by = :changed_by, changed_at = :changed_at where id = :id";
    @Override
    public void updateProfile(User user) {
        Map<String, Object> params = new HashMap<>(7);
        params.put(DBUtils.id, user.getId());
        params.put(DBUtils.login, user.getLogin());
        params.put(DBUtils.full_name, user.getFullName());
        params.put(DBUtils.email, user.getEmail());
        params.put(DBUtils.zip, user.getZip());
        params.put(DBUtils.changed_by, user.getChangedBy());
        params.put(DBUtils.changed_at, user.getChangedAt());
        parameterJdbcTemplate.update(updateProfileSql, params);
    }

    private static final String getUserByEmailSql = "select * from t_user u where u.email = :email";
    @Override
    public User getUserByEmail(String email) {
        User user = null;

        try {
            user = parameterJdbcTemplate.queryForObject(getUserByEmailSql,
                    Collections.singletonMap(DBUtils.email, email), userMapper);
        } catch (EmptyResultDataAccessException e) {
            // skip
        }
        if (user == null) {
            throw new UserNotFoundException("User [" + email + "] not found");
        }

        user.setPermissions(permissionDao.getRolePermissions(user.getRoleId()));

        return user;
    }

    private static final String searchSql = "select id, login, full_name, email, last_login, enabled from t_user";
    private static final String countSql = "select count(id) from t_user";
    @Override
    public ListPaginatedResult<UserListDTO> search(UserSearchQuery userSearchQuery) {
        int rowsPerPage = userSearchQuery.getPageSize();
        int pageNum = userSearchQuery.getPage();
        int offset = (pageNum - 1) * rowsPerPage;

        ListPaginatedResult<UserListDTO> result = new ListPaginatedResult<>();
        StringBuilder query = new StringBuilder(searchSql);
        query.append(" limit ").append(offset);
        query.append(", ").append(rowsPerPage);

        List<UserListDTO> users = parameterJdbcTemplate.query(query.toString(), userListDTOMapper);
        result.setList(users);

        int total = getJdbcTemplate().queryForObject(countSql, Integer.class);
        result.setTotal(total);
        return result;
    }

    private static final String createVerificationTokenSql = "insert into verification_token "
            + "(id, user_id, token, token_type, expiry_date, expired) "
            + "values(:id, :user_id, :token, :token_type, :expiry_date, :expired)";
    @Override
    public void createVerificationToken(VerificationToken verificationToken) {
        long id = DBUtils.getNextId(getJdbcTemplate(), "verification_token");
        Map<String, Object> params = new HashMap<>(6);
        params.put(DBUtils.id, id);
        params.put(DBUtils.user_id, verificationToken.getUserId());
        params.put(DBUtils.token, verificationToken.getToken());
        params.put(DBUtils.token_type, verificationToken.getTokenType());
        params.put(DBUtils.expiry_date, verificationToken.getExpiryDate());
        params.put(DBUtils.expired, verificationToken.isExpired());
        parameterJdbcTemplate.update(createVerificationTokenSql, params);
    }

    private static final String updateVerificationTokenSql = "update verification_token "
            + "set token = :token, expiry_date = :expiry_date, expired = :expired where id = :id";
    @Override
    public void updateVerificationToken(VerificationToken verificationToken) {
        Map<String, Object> params = new HashMap<>(4);
        params.put(DBUtils.id, verificationToken.getId());
        params.put(DBUtils.token, verificationToken.getToken());
        params.put(DBUtils.expiry_date, verificationToken.getExpiryDate());
        params.put(DBUtils.expired, verificationToken.isExpired());
        parameterJdbcTemplate.update(updateVerificationTokenSql, params);
    }

    private static final String getTokenSql = "select * from verification_token where user_id = :user_id and token_type = :token_type";
    @Override
    public VerificationToken getToken(long userId, VerificationTokenType tokenType) {
        Map<String, Object> params = new HashMap<>(2);
        params.put(DBUtils.user_id, userId);
        params.put(DBUtils.token_type, tokenType.getValue());

        try {
            return parameterJdbcTemplate.queryForObject(getTokenSql, params, tokenMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private static final String getViewDTOSql = "select u.*, r.name as role from t_user u "
            + "inner join role r on r.id = u.role_id "
            + "where u.id = :id";
    @Override
    public UserViewDTO getViewDTO(long userId) {
        return parameterJdbcTemplate.queryForObject(
                getViewDTOSql, Collections.singletonMap(DBUtils.id, userId), userViewDTOMapper);
    }

    private static final String getUsersCountSql = "select count(id) from t_user";
    @Override
    public int getUsersCount() {
        return getJdbcTemplate().queryForObject(getUsersCountSql, Integer.class);
    }
}
