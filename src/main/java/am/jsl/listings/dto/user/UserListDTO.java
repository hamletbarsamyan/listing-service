package am.jsl.listings.dto.user;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * The UserListDTO is used for displaying user data in lists.
 *
 * @author hamlet
 */
public class UserListDTO extends BaseUserDTO implements Serializable {

    /**
     * Indicates whether this user is enabled
     */
    private boolean enabled;

    /**
     * The last logged in date
     */
    private Timestamp lastLogin;

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
}
