package am.jsl.listings.domain.user;

import am.jsl.listings.domain.Descriptive;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * The role domain object.
 *
 * @author hamlet
 */
public class Role extends Descriptive implements Serializable, GrantedAuthority {

	/**
	 * If true then role is not deletable.
	 */
	private boolean defaultRole;

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

	/** The role permissions */
	private List<Long> permissions;

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

	@Override
	public String toString() {
		return "Role{" +
				"id=" + getId() +
				", name=" + getName() +
				", permissions=" + permissions +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null)
			return false;

		if (o instanceof Role) {
			final Role other = (Role) o;
			return Objects.equals(getId(), other.getId())
					&& Objects.equals(getName(), other.getName());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getName());
	}

	@Override
	public String getAuthority() {
		return getName();
	}
}
