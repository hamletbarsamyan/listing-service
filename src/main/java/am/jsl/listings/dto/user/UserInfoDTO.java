package am.jsl.listings.dto.user;

import am.jsl.listings.domain.user.Permission;
import am.jsl.listings.domain.user.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains user information.
 *
 * @author hamlet
 */
public class UserInfoDTO extends BaseUserDTO implements Serializable {

    /**
     * User permissions
     */
    private List<String> permissions;

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
     * Creates a User DTO object from User domain object.
     *
     * @param user the User
     * @return the UserDTO
     */
    public static UserInfoDTO from(User user) {
        UserInfoDTO userDTO = new UserInfoDTO();
        userDTO.setId(user.getId());
        userDTO.setLogin(user.getUsername());
        userDTO.setFullName(user.getFullName());
        userDTO.setIcon(user.getIcon());

        List<String> permissions = new ArrayList<>();

        for (Permission permission : user.getPermissions()) {
            permissions.add(permission.getName());
        }
        userDTO.setPermissions(permissions);

        return userDTO;
    }
}
