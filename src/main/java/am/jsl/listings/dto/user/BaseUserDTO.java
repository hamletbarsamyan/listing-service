package am.jsl.listings.dto.user;

import am.jsl.listings.dto.BaseDTO;
import am.jsl.listings.util.Constants;
import am.jsl.listings.util.TextUtils;
import am.jsl.listings.util.UserUtils;

import java.io.Serializable;

/**
 * The BaseUserDTO contains common fields and methods used by user dto classes.
 *
 * @author hamlet
 */
public class BaseUserDTO extends BaseDTO implements Serializable {
    /**
     * The user login
     */
    private String login;

    /**
     * The user full name
     */
    private String fullName;

    /**
     * The user email
     */
    private String email;

    /**
     * The user icon server
     */
    private String iconServer;

    /**
     * The user icon
     */
    private String icon;

    /**
     * The zip code
     */
    private String zip;

    /**
     * The icon path
     */
    private String iconPath;

    /**
     * Gets login.
     *
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Sets login.
     *
     * @param login the login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Gets full name.
     *
     * @return the full name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Sets full name.
     *
     * @param fullName the full name
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets icon.
     *
     * @return the icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * Sets icon.
     *
     * @param icon the icon
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * Initializes the icon path from server and icon.
     */
    public void initIconPath() {
        String path = UserUtils.getIconPath(iconServer, icon, getId());
        setIconPath(path);
    }

    /**
     * Gets icon path.
     *
     * @return the icon path
     */
    public String getIconPath() {
        return iconPath;
    }

    /**
     * Sets icon path.
     *
     * @param iconPath the icon path
     */
    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    /**
     * Gets zip.
     *
     * @return the zip
     */
    public String getZip() {
        return zip;
    }

    /**
     * Sets zip.
     *
     * @param zip the zip
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * Gets icon server.
     *
     * @return the icon server
     */
    public String getIconServer() {
        return iconServer;
    }

    /**
     * Sets icon server.
     *
     * @param iconServer the icon server
     */
    public void setIconServer(String iconServer) {
        this.iconServer = iconServer;
    }
}
