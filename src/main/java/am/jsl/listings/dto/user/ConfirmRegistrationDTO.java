package am.jsl.listings.dto.user;

import java.io.Serializable;

/**
 * The ConfirmRegistrationDTO is used for confirming (via email) user registration.
 *
 * @author hamlet
 */
public class ConfirmRegistrationDTO implements Serializable {

    /**
     * The user id
     */
    private long userId;

    /**
     * The verification token
     */
    private String token;


    /**
     * Gets user id.
     *
     * @return the user id
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Sets user id.
     *
     * @param userId the user id
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * Gets token.
     *
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets token.
     *
     * @param token the token
     */
    public void setToken(String token) {
        this.token = token;
    }
}
