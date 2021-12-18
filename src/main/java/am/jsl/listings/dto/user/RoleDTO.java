package am.jsl.listings.dto.user;


import am.jsl.listings.domain.user.Role;
import am.jsl.listings.dto.DescriptiveDTO;

import java.io.Serializable;
import java.util.List;

/**
 * The RoleDTO is used for transferring role data.
 *
 * @author hamlet
 */
public class RoleDTO extends DescriptiveDTO implements Serializable {

    /**
     * True if role is default
     */
    private boolean defaultRole;

    /**
     * The list of permissions
     */
    private List<Long> permissions;


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
    public List<Long> getPermissions() {
        return permissions;
    }

    /**
     * Sets permissions.
     *
     * @param permissions the permissions
     */
    public void setPermissions(List<Long> permissions) {
        this.permissions = permissions;
    }

    /**
     * Creates a RoleDTO object from Role domain object.
     *
     * @param role the Role
     * @return the RoleDTO
     */
    public static RoleDTO from(Role role) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(role.getId());
        roleDTO.setName(role.getName());
        roleDTO.setDescription(role.getDescription());
        roleDTO.setDefaultRole(role.isDefaultRole());
        roleDTO.setPermissions(role.getPermissions());
        return roleDTO;
    }

    /**
     * Converts this RoleDTO to Role domain object.
     *
     * @return the Role
     */
    public Role toRole() {
        Role role = new Role();
        role.setId(getId());
        role.setName(getName());
        role.setDescription(getDescription());
        role.setDefaultRole(isDefaultRole());
        role.setPermissions(getPermissions());
        return role;
    }
}
