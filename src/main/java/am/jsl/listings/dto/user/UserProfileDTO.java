package am.jsl.listings.dto.user;

import am.jsl.listings.domain.user.User;

import java.io.Serializable;
import java.util.Date;

/**
 * The UserProfileDTO is used for managing user profile.
 *
 * @author hamlet
 */
public class UserProfileDTO extends BaseUserDTO implements Serializable {

    /**
     * The user last logged in date
     */
    private Date lastLogin;

    /**
     * The Created at.
     */
    private Date createdAt;

    /**
     * Creates a User DTO object from User domain object.
     *
     * @param user the User
     * @return the UserDTO
     */
    public static UserProfileDTO from(User user) {
        UserProfileDTO userDTO = new UserProfileDTO();
        userDTO.setId(user.getId());
        userDTO.setLogin(user.getLogin());
        userDTO.setFullName(user.getFullName());
        userDTO.setEmail(user.getEmail());
        userDTO.setZip(user.getZip());
        userDTO.setCreatedAt(user.getCreatedAt());
        userDTO.setLastLogin(user.getLastLogin());

        return userDTO;
    }

    /**
     * Gets last login.
     *
     * @return the last login
     */
    public Date getLastLogin() {
        return lastLogin;
    }

    /**
     * Sets last login.
     *
     * @param lastLogin the last login
     */
    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    /**
     * Gets created at.
     *
     * @return the created at
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets created at.
     *
     * @param createdAt the created at
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
