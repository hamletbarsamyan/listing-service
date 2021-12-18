package am.jsl.listings.domain.attribute;

import am.jsl.listings.domain.Translation;

import java.io.Serializable;

/**
 * The AttributeValueTranslation contains the attribute value translation fields.
 *
 * @author hamlet
 */
public class AttributeValueTranslation extends Translation implements Serializable {
    /**
     * The attribute value
     */
    private String valueTr;

    /**
     * Default constructor.
     */
    public AttributeValueTranslation() {
        super();
    }

    /**
     * Constructs a new translation with th given locale.
     *
     * @param locale the locale
     */
    public AttributeValueTranslation(String locale) {
        super(locale);
    }

    /**
     * Gets valueTr.
     *
     * @return the valueTr
     */
    public String getValueTr() {
        return valueTr ;
    }

    /**
     * Sets valueTr.
     *
     * @param valueTr the valueTr
     */
    public void setValueTr(String valueTr) {
        this.valueTr = valueTr;
    }
}
