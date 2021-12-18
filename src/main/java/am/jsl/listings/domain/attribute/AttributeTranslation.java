package am.jsl.listings.domain.attribute;

import am.jsl.listings.domain.DescriptiveTranslation;

import java.io.Serializable;

/**
 * The AttributeTranslation contains the attribute translation fields.
 *
 * @author hamlet
 */
public class AttributeTranslation extends DescriptiveTranslation implements Serializable {
    /**
     * Attribute extra information
     */
    private String extraInfo;

    /**
     * Default constructor.
     *
     */
    public AttributeTranslation() {
        super();
    }

    /**
     * Constructs a new translation with th given locale.
     *
     * @param locale the locale
     */
    public AttributeTranslation(String locale) {
        super(locale);
    }

    /**
     * Gets extra info.
     *
     * @return the extra info
     */
    public String getExtraInfo() {
        return extraInfo;
    }

    /**
     * Sets extra info.
     *
     * @param extraInfo the extra info
     */
    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }
}
