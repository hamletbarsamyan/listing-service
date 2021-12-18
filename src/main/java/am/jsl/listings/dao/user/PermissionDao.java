package am.jsl.listings.dao.user;

import am.jsl.listings.dao.BaseDao;
import am.jsl.listings.domain.user.Permission;

import java.util.List;

/**
 * The Dao interface for accessing {@link Permission} domain object.
 * @author hamlet
 */
public interface PermissionDao  extends BaseDao<Permission> {

    /**
     * Returns role permissions.
     * @param roleId  the role id
     * @return the permissions
     */
    List<Permission> getRolePermissions(long roleId);
}
