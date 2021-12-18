package am.jsl.listings.dao.user;

import am.jsl.listings.dao.BaseDaoImpl;
import am.jsl.listings.dao.DBUtils;
import am.jsl.listings.dao.user.mapper.RoleMapper;
import am.jsl.listings.domain.user.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The implementation of Dao interface for accessing {@link Role} domain object.
 *
 * @author hamlet
 */
@Repository("roleDao")
@Lazy
public class RoleDaoImpl extends BaseDaoImpl<Role> implements RoleDao {

    private RoleMapper roleMapper = new RoleMapper();

    @Autowired
    @Qualifier("permissionDao")
    private PermissionDao permissionDao;

    @Autowired
    RoleDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    private static final String getSql = "select * from role where id = :id";

    @Override
    public Role get(long id) {
        try {
            Role role = get(id, getSql, roleMapper);

            if (role == null) {
                return null;
            }
            List<Long> permissions = getRolePermissionIds(role.getId());
            role.setPermissions(permissions);
            return role;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private static final String getByNameSql = "select * from role "
            + "where name = :name";

    @Override
    public Role getByName(String name) {
        Role role = parameterJdbcTemplate.queryForObject(
                getByNameSql, Collections.singletonMap(DBUtils.name, name), roleMapper);

        List<Long> permissions = getRolePermissionIds(role.getId());
        role.setPermissions(permissions);
        return role;
    }

    private static final String getDefaultRoleSql = "select * from role where default_role = 1";
    @Override
    public Role getDefaultRole() {
        try {
            List<Role> roles = getJdbcTemplate().query(getDefaultRoleSql, roleMapper);

            return !roles.isEmpty() ? roles.get(0) : null;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private static final String listSql = "select * from role order by name";

    @Override
    public List<Role> list() {
        return getJdbcTemplate().query(listSql, roleMapper);
    }

    private static final String createSql = "insert into role "
            + "(id, default_role, name, description) values(:id, :default_role, :name, :description)";

    @Override
    public void create(Role role) {
        long id = DBUtils.getNextId(getJdbcTemplate(), "role");
        role.setId(id);
        Map<String, Object> params = new HashMap<>(3);
        params.put(DBUtils.id, role.getId());
        params.put(DBUtils.default_role, 0);
        params.put(DBUtils.name, role.getName());
        params.put(DBUtils.description, role.getDescription());
        parameterJdbcTemplate.update(createSql, params);

        saveRolePermissions(role);
    }

    private static final String updateSql = "update role "
            + "set name = :name, description = :description where id = :id";

    @Override
    public void update(Role role) {
        Map<String, Object> params = new HashMap<>(3);
        params.put(DBUtils.id, role.getId());
        params.put(DBUtils.name, role.getName());
        params.put(DBUtils.description, role.getDescription());
        parameterJdbcTemplate.update(updateSql, params);

        deleteRolePermissions(role.getId());
        saveRolePermissions(role);
    }

    private static final String createRolePermissionsSql = "insert into role_permissions " +
            "(role_id, permission_id) VALUES (?, ?)";

    private void saveRolePermissions(final Role role) {
        List<Long> permissions = role.getPermissions();

        if (CollectionUtils.isEmpty(permissions)) {
            return;
        }
        getJdbcTemplate().batchUpdate(createRolePermissionsSql,
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i)
                            throws SQLException {
                        ps.setLong(1, role.getId());
                        ps.setLong(2, permissions.get(i));
                    }

                    public int getBatchSize() {
                        return permissions.size();
                    }
                });
    }

    private static final String getRolePermissionIdsSql = "select permission_id from role_permissions where role_id = :role_id";
    @Override
    public List<Long> getRolePermissionIds(long roleId) {
        try {
            return parameterJdbcTemplate.queryForList(
                    getRolePermissionIdsSql, Collections.singletonMap(DBUtils.role_id, roleId), Long.class);
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    private static final String deleteRolePermissionsSql = "delete from role_permissions where role_id = ?";

    private void deleteRolePermissions(long roleId) {
        getJdbcTemplate().update(deleteRolePermissionsSql,
                new Object[]{roleId});
    }

    private static final String canDeleteRoleSql = "select id from t_user " +
            "where role_id = ?";
    @Override
    public boolean canDelete(long id) {
        Role role = get(id, getSql, roleMapper);

        if (role.isDefaultRole()) {
            return false;
        }

        List<Long> list = getJdbcTemplate().queryForList(canDeleteRoleSql,
                new Object[]{id}, Long.class);
        if (list.size() > 0) {
            return false;
        }
        return true;
    }

    private static final String deleteRoleSql = "delete from role where id = ?";
    @Override
    public void delete(long id) {
        deleteRolePermissions(id);
        getJdbcTemplate().update(deleteRoleSql, new Object[]{id});
    }

    private static final String roleExistsSql = "select id from role where LOWER(name) = ? and id != ?";
    @Override
    public boolean exists(String name, long id) {
        List<Long> list = getJdbcTemplate().queryForList(roleExistsSql,
                new Object[]{name.toLowerCase(), id}, Long.class);
        return list.size() > 0;
    }
}
