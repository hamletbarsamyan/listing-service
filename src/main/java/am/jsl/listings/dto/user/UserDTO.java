package am.jsl.listings.dto.user;

import am.jsl.listings.domain.user.User;
import am.jsl.listings.util.Constants;
import am.jsl.listings.util.TextUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * The User dto.
 *
 * @author hamlet
 */
public class UserDTO extends BaseUserDTO implements Serializable {

    /**
     * The user password
     */
    private String password;

    /**
     * The user confirm password
     */
    private String confirmPassword;

    /**
     * Indicates whether this user is enabled
     */
    private boolean enabled;

    /**
     * The role id of this user
     */
    private long role;

    /**
     * The user last logged in date
     */
    private Date lastLogin;

    /**
     * The Created at.
     */
    private Date createdAt;

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets confirm password.
     *
     * @return the confirm password
     */
    public String getConfirmPassword() {
        return confirmPassword;
    }

    /**
     * Sets confirm password.
     *
     * @param confirmPassword the confirm password
     */
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    /**
     * Is enabled boolean.
     *
     * @return the boolean
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets enabled.
     *
     * @param enabled the enabled
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


    /**
     * Gets role.
     *
     * @return the role
     */
    public long getRole() {
        return role;
    }

    /**
     * Sets role.
     *
     * @param role the role
     */
    public void setRole(long role) {
        this.role = role;
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

    /**
     * Creates a User DTO object from User domain object.
     *
     * @param user the User
     * @return the UserDTO
     */
    public static UserDTO from(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setLogin(user.getUsername());
        userDTO.setFullName(user.getFullName());
        userDTO.setEmail(user.getEmail());
        userDTO.setIcon(user.getIcon());
        userDTO.setEnabled(user.isEnabled());
        userDTO.setRole(user.getRoleId());
        userDTO.setZip(user.getZip());
        userDTO.setLastLogin(user.getLastLogin());
        userDTO.setCreatedAt(user.getCreatedAt());
        return userDTO;
    }

    /**
     * Converts this user dto to User domain object.
     *
     * @return the User domain object
     */
    public User toUser() {
        User user = new User();
        user.setId(getId());
        user.setLogin(getLogin());
        user.setPassword(password);
        user.setFullName(getFullName());
        user.setEmail(getEmail());
        user.setIcon(getIcon());
        user.setEnabled(enabled);
        user.setRoleId(role);
        user.setZip(getZip());
        return user;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLogin(), getEmail());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final UserDTO other = (UserDTO) obj;
        return Objects.equals(this.getId(), other.getId())
                && Objects.equals(this.getLogin(), other.getLogin())
                && Objects.equals(this.getEmail(), other.getEmail());
    }
}
