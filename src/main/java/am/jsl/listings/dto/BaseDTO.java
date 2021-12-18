package am.jsl.listings.dto;

import java.util.Objects;

/**
 * The BaseDTO is the root for DTO classes.
 * DTO classes are used to transfer objects between application layers.
 *
 * @author hamlet
 */
public class BaseDTO {
    /**
     * The internal identifier.
     */
    private long id;

    /**
     * Is create boolean.
     *
     * @return the boolean
     */
    public boolean isCreate() {
        return getId() == 0;
    }

    /**
     * Getter for property 'id'.
     *
     * @return Value for property 'id'.
     */
    public long getId() {
        return id;
    }

    /**
     * Setter for property 'id'.
     *
     * @param id Value to set for property 'id'.
     */
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final BaseDTO other = (BaseDTO) obj;
        return Objects.equals(this.id, other.id);
    }
}
