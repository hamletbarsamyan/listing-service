package am.jsl.listings.service.user;

import am.jsl.listings.dao.user.PermissionDao;
import am.jsl.listings.domain.user.Permission;
import am.jsl.listings.service.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The service implementation of the {@link PermissionService}.
 * @author hamlet
 */
@Service("permissionService")
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements PermissionService {
    private PermissionDao permissionDao;
    /**
     * Setter for property 'permissionDao'.
     *
     * @param permissionDao Value to set for property 'permissionDao'.
     */
    @Autowired
    public void setPermissionDao(@Qualifier("permissionDao") PermissionDao permissionDao) {
        setBaseDao(permissionDao);
        this.permissionDao = permissionDao;
    }

    @Override
    public List<Permission> getRolePermissions(long roleId) {
        return permissionDao.getRolePermissions(roleId);
    }
}
