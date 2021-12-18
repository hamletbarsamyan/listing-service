package am.jsl.listings.domain;

/**
 * The Translation contains object's translation fields for a locale.
 *
 * @author hamlet
 */
public class Translation extends BaseEntity {
    /**
     * The locale
     */
    private String locale;

    /**
     * Constructs a new translation with th given locale.
     *
     * @param locale the locale
     */
    public Translation(String locale) {
        this.locale = locale;
    }

    /**
     * Default constructor.
     *
     */
    public Translation() {
        super();
    }

    /**
     * Gets locale.
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
}
