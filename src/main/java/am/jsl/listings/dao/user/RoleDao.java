package am.jsl.listings.dao.user;

import am.jsl.listings.dao.BaseDao;
import am.jsl.listings.domain.user.Role;

import java.util.List;

/**
 * The Dao interface for accessing {@link Role} domain object.
 * @author hamlet
 */
public interface RoleDao extends BaseDao<Role> {

    /**
     * Returns a role by name.
     * @param name  the role name
     * @return the permissions
     */
    Role getByName(String name);

    /**
     * Returns a default role.
     * @return the th default role
     */
    Role getDefaultRole();

    /**
     * Returns permission ids by role.
     * @param roleId  the role id
     * @return the permission ids
     */
    List<Long> getRolePermissionIds(long roleId);
}
