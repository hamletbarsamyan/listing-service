package am.jsl.listings.domain.user;

import am.jsl.listings.domain.Descriptive;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

/**
 * The permission domain object.
 *
 * @author hamlet
 */
public class Permission extends Descriptive implements Serializable, GrantedAuthority {
    @Override
    public String getAuthority() {
        return getName();
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + getId() +
                ", name=" + getName() +
                '}';
    }
}
