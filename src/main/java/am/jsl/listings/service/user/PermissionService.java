package am.jsl.listings.service.user;

import am.jsl.listings.domain.user.Permission;
import am.jsl.listings.service.BaseService;

import java.util.List;

/**
 * Service interface which defines all the methods for working with {@link Permission} domain object.
 * @author hamlet
 */
public interface PermissionService extends BaseService<Permission> {
    /**
     * Returns role permissions.
     * @param roleId  the role id
     * @return the permissions
     */
    List<Permission> getRolePermissions(long roleId);
}
