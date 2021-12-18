package am.jsl.listings.dto.user;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Contains user information.
 *
 * @author hamlet
 */
public class UserViewDTO extends BaseUserDTO implements Serializable {

    /**
     * The last logged in time stamp
     */
    private Timestamp lastLogin;

    /**
     * The user status
     */
    private boolean enabled;

    /**
     * User role name
     */
    private String role;

    /**
     * The creation time stamp
     */
    private Timestamp createdAt;

    /**
     * Gets last login.
     *
     * @return the last login
     */
    public Timestamp getLastLogin() {
        return lastLogin;
    }

    /**
     * Sets last login.
     *
     * @param lastLogin the last login
     */
    public void setLastLogin(Timestamp lastLogin) {
        this.lastLogin = lastLogin;
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
    public String getRole() {
        return role;
    }

    /**
     * Sets role.
     *
     * @param role the role
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Gets created at.
     *
     * @return the created at
     */
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets created at.
     *
     * @param createdAt the created at
     */
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
