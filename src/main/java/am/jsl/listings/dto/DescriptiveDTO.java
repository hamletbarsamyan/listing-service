package am.jsl.listings.dto;

import java.io.Serializable;

/**
 * the DescriptiveDTO extends NamedDTO and contains description field.
 * Used as a base class for objects needing these properties.
 * @author hamlet
 */
public class DescriptiveDTO extends NamedDTO implements Serializable {
    /**
     * The description.
     */
    private String description;

    /**
     * Getter for property 'description'.
     *
     * @return Value for property 'description'.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter for property 'description'.
     *
     * @param description Value to set for property 'description'.
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
