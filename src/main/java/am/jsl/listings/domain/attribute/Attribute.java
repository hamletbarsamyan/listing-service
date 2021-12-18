package am.jsl.listings.domain.attribute;

import am.jsl.listings.domain.Descriptive;

import java.io.Serializable;

/**
 * The attribute domain object.
 *
 * @author hamlet
 */
public class Attribute extends Descriptive implements Serializable {

    /**
     * The attribute type
     */
    private String attrType;

    /**
     * Attribute extra information
     */
    private String extraInfo;

    /**
     * Gets attr type.
     *
     * @return the attr type
     */
    public String getAttrType() {
        return attrType;
    }

    /**
     * Sets attr type.
     *
     * @param attrType the attr type
     */
    public void setAttrType(String attrType) {
        this.attrType = attrType;
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
