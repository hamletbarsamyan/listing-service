package am.jsl.listings.domain;


import java.io.Serializable;
import java.util.Objects;

/**
 * Domain object with common properties.
 * Used as a base class for objects needing these properties.
 *
 * @author hamlet
 */
public class BaseEntity implements Serializable {

    /**
     * The internal identifier. See # {@link #getId()} see {@link #setId(long)}.
     */
    protected long id;

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("%s(id=%d)", this.getClass().getSimpleName(), this.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null)
            return false;

        if (o instanceof BaseEntity) {
            final BaseEntity other = (BaseEntity) o;
            return Objects.equals(getId(), other.getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

}
