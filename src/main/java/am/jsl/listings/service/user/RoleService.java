package am.jsl.listings.service.user;

import am.jsl.listings.domain.user.Role;
import am.jsl.listings.service.BaseService;

/**
 * Service interface which defines all the methods for working with {@link Role} domain object.
 * @author hamlet
 */
public interface RoleService extends BaseService<Role> {
    /**
     * Returns a default role.
     * @return the th default role
     */
    Role getDefaultRole();
}
