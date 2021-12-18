package am.jsl.listings.dto.user;


import am.jsl.listings.domain.user.Permission;
import am.jsl.listings.domain.user.Role;
import am.jsl.listings.dto.DescriptiveDTO;

import java.io.Serializable;
import java.util.List;

/**
 * The RoleInfoDTO is used for transferring role data.
 *
 * @author hamlet
 */
public class RoleInfoDTO extends DescriptiveDTO implements Serializable {

    /**
     * True if role is default
     */
    private boolean defaultRole;

    /**
     * The list of permissions
     */
    private List<String> permissions;


    /**
     * Is default role boolean.
     *
     * @return the boolean
     */
    public boolean isDefaultRole() {
        return defaultRole;
    }

    /**
     * Sets default role.
     *
     * @param defaultRole the default role
     */
    public void setDefaultRole(boolean defaultRole) {
        this.defaultRole = defaultRole;
    }


    /**
     * Gets permissions.
     *
     * @return the permissions
     */
    public List<String> getPermissions() {
        return permissions;
    }

    /**
     * Sets permissions.
     *
     * @param permissions the permissions
     */
    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    /**
     * Creates a RoleDTO object from Role domain object.
     *
     * @param role the Role
     * @return the RoleDTO
     */
    public static RoleInfoDTO from(Role role) {
        RoleInfoDTO roleDTO = new RoleInfoDTO();
        roleDTO.setId(role.getId());
        roleDTO.setName(role.getName());
        roleDTO.setDescription(role.getDescription());
        roleDTO.setDefaultRole(role.isDefaultRole());
        return roleDTO;
    }
}
