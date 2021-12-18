package am.jsl.listings.domain;

import java.io.Serializable;

/**
 * the DescriptiveTranslation extends NamedTranslation and contains description field.
 * Used as a base class for objects needing these properties.
 * @author hamlet
 */
public class DescriptiveTranslation extends NamedTranslation implements Serializable {
    /**
     * The description.
     */
    private String description;

    /**
     * Constructs a new translation with th given locale.
     *
     * @param locale the locale
     */
    public DescriptiveTranslation(String locale) {
        super(locale);
    }

    /**
     * Default constructor.
     *
     */
    public DescriptiveTranslation() {
        super();
    }

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
