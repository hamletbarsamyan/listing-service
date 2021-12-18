package am.jsl.listings.domain.attribute;

import am.jsl.listings.domain.BaseEntity;

import java.io.Serializable;

/**
 * The attribute value domain object.
 *
 * @author hamlet
 */
public class AttributeValue extends BaseEntity implements Serializable {

    /**
     * The attribute id
     */
    private long attributeId;

    /**
     * The parent value id
     */
    private long parentValueId;

    /**
     * The value
     */
    private String value;

    /**
     * The value translation
     */
    private String valueTr;

    /**
     * The sort order
     */
    private int sortOrder;

    /**
     * The locale
     */
    private String locale;

    /**
     * Gets attribute id.
     *
     * @return the attribute id
     */
    public long getAttributeId() {
        return attributeId;
    }

    /**
     * Sets attribute id.
     *
     * @param attributeId the attribute id
     */
    public void setAttributeId(long attributeId) {
        this.attributeId = attributeId;
    }

    /**
     * Gets parent value id.
     *
     * @return the parent value id
     */
    public long getParentValueId() {
        return parentValueId;
    }

    /**
     * Sets parent value id.
     *
     * @param parentValueId the parent value id
     */
    public void setParentValueId(long parentValueId) {
        this.parentValueId = parentValueId;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets value.
     *
     * @param value the value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets value tr.
     *
     * @return the value tr
     */
    public String getValueTr() {
        return valueTr;
    }

    /**
     * Sets value tr.
     *
     * @param valueTr the value tr
     */
    public void setValueTr(String valueTr) {
        this.valueTr = valueTr;
    }

    /**
     * Gets sort order.
     *
     * @return the sort order
     */
    public int getSortOrder() {
        return sortOrder;
    }

    /**
     * Sets sort order.
     *
     * @param sortOrder the sort order
     */
    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
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
