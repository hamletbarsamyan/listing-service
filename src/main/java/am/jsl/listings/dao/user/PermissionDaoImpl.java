package am.jsl.listings.dao.user;

import am.jsl.listings.dao.BaseDaoImpl;
import am.jsl.listings.dao.DBUtils;
import am.jsl.listings.dao.user.mapper.PermissionMapper;
import am.jsl.listings.domain.user.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;

/**
 * The implementation of Dao interface for accessing {@link Permission} domain object.
 *
 * @author hamlet
 */
@Repository("permissionDao")
@Lazy
public class PermissionDaoImpl extends BaseDaoImpl<Permission> implements PermissionDao {
    private PermissionMapper permissionMapper = new PermissionMapper();

    @Autowired
    PermissionDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    private static final String getRolePermissionsSql = "select p.* "
            + "from permission p "
            + "inner join role_permissions rp on p.id = rp.permission_id  "
            + "where rp.role_id = :role_id";

    @Override
    public List<Permission> getRolePermissions(long roleId) {
        try {
            return parameterJdbcTemplate.query(
                    getRolePermissionsSql, Collections.singletonMap(DBUtils.role_id, roleId), permissionMapper);
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    private static final String listSql = "select * from permission order by name";

    @Override
    public List<Permission> list() {
        return getJdbcTemplate().query(listSql, permissionMapper);
    }
}
