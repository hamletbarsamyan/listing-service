package am.jsl.listings.domain;

import java.io.Serializable;

/**
 * The NamedTranslation is used for translating objects with name fields.
 * Used as a base class for objects needing these properties.
 * @author hamlet
 */
public class NamedTranslation extends Translation implements Serializable {
    /**
     * The name.
     */
    private String name;

    /**
     * Constructs a new translation with th given locale.
     *
     * @param locale the locale
     */
    public NamedTranslation(String locale) {
        super(locale);
    }

    /**
     * Default constructor.
     *
     */
    public NamedTranslation() {
        super();
    }

    /**
     * Getter for property 'name'.
     *
     * @return Value for property 'name'.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for property 'name'.
     *
     * @param name Value to set for property 'name'.
     */
    public void setName(String name) {
        this.name = name;
    }
}
