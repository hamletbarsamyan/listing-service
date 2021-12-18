package am.jsl.listings.domain.user;

import am.jsl.listings.domain.BaseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

/**
 * The user domain object that implements UserDetails and will used
 * Spring Security for security purposes.
 *
 * @author hamlet
 */
public class User extends BaseEntity implements UserDetails, Serializable {

    /**
     * The user login
     */
    private String login;

    /**
     * The user password
     */
    private String password;

    /**
     * The user full name
     */
    private String fullName;

    /**
     * The user email
     */
    private String email;

    /**
     * The zip code
     */
    private String zip;

    /**
     * The user icon
     */
    private String icon;

    /**
     * The user last logged in date
     */
    private Date lastLogin;

    /**
     * Indicates whether this user is enabled
     */
    private boolean enabled = true;

    /**
     * User permissions
     */
    private List<Permission> permissions;

    /**
     * The user role
     */
    private long roleId;

    /**
     * The user authorities
     */
    private Set<GrantedAuthority> authorities;

    /**
     * The Created at.
     */
    private Date createdAt;

    /**
     * The Changed by.
     */
    private long changedBy;

    /**
     * The Changed at.
     */
    private Date changedAt;

    /**
     * Default constructor
     */
    public User() {
        super();
    }

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

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return getLogin();
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
     * Sets enabled.
     *
     * @param enabled the enabled
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
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
     * Gets permissions.
     *
     * @return the permissions
     */
    public List<Permission> getPermissions() {
        return permissions;
    }

    /**
     * Sets permissions.
     *
     * @param permissions the permissions
     */
    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    /**
     * Gets role id.
     *
     * @return the role id
     */
    public long getRoleId() {
        return roleId;
    }

    /**
     * Sets role id.
     *
     * @param roleId the role id
     */
    public void setRoleId(long roleId) {
        this.roleId = roleId;
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

    @Override
    public int hashCode() {
        return Objects.hash(getId(), login);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        final User other = (User) obj;
        return Objects.equals(this.getId(), other.getId())
                && Objects.equals(this.login, other.login);
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        if (authorities == null) {
            authorities = new HashSet<>();
            authorities.addAll(permissions);
        }

        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        //return true = account is valid / not expired
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        //return true = account is not locked
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        //return true = password is valid / not expired
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
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
     * Gets changed by.
     *
     * @return the changed by
     */
    public long getChangedBy() {
        return changedBy;
    }

    /**
     * Sets changed by.
     *
     * @param changedBy the changed by
     */
    public void setChangedBy(long changedBy) {
        this.changedBy = changedBy;
    }

    /**
     * Gets changed at.
     *
     * @return the changed at
     */
    public Date getChangedAt() {
        return changedAt;
    }

    /**
     * Sets changed at.
     *
     * @param changedAt the changed at
     */
    public void setChangedAt(Date changedAt) {
        this.changedAt = changedAt;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + getId() + '\'' +
                "login='" + login + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", enabled=" + enabled +
                ", roleId=" + roleId +
                '}';
    }
}
