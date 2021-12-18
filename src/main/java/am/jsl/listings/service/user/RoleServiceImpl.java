package am.jsl.listings.service.user;

import am.jsl.listings.dao.user.RoleDao;
import am.jsl.listings.domain.user.Role;
import am.jsl.listings.service.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * The service implementation of the {@link RoleService}.
 * @author hamlet
 */
@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {

    /** The role dao */
    private RoleDao roleDao;

    @Override
    public Role getDefaultRole() {
        return roleDao.getDefaultRole();
    }

    /**
     * Setter for property 'roleDao'.
     *
     * @param roleDao Value to set for property 'roleDao'.
     */
    @Autowired
    public void setRoleDao(@Qualifier("roleDao") RoleDao roleDao) {
        setBaseDao(roleDao);
        this.roleDao = roleDao;
    }
}
