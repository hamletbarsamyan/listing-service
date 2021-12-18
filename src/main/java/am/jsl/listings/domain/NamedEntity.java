package am.jsl.listings.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 * Domain object that extends BaseEntity and contains name field.
 * Used as a base class for objects needing these properties.
 *
 * @author hamlet
 */
public class NamedEntity extends BaseEntity implements Serializable {

    /**
     * The name
     */
    protected String name;

    /**
     * The locale
     */
    protected String locale;

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets locale
     *
     * @return the locale
     */
    public String getLocale() {
        return locale;
    }

    /**
     * Sets locale.
     *
     * @param locale the locale
     */
    public void setLocale(String locale) {
        this.locale = locale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        NamedEntity that = (NamedEntity) o;
        return locale == that.locale &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, locale);
    }
}
