package am.jsl.listings.domain.user;

import am.jsl.listings.domain.BaseEntity;

import java.io.Serializable;

/**
 * The user contact domain object.
 *
 * @author hamlet
 */
public class UserContact extends BaseEntity implements Serializable {
    /**
     * The User id.
     */
    private long userId;

     /**
     * The contact type (EMAIL, PHONE,...)
     */
    private byte contactType;

    private String contact;

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Sets user id.
     *
     * @param userId the user id
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * Gets contact type.
     *
     * @return the contact type
     */
    public byte getContactType() {
        return contactType;
    }

    /**
     * Sets contact type.
     *
     * @param contactType the contact type
     */
    public void setContactType(byte contactType) {
        this.contactType = contactType;
    }

    /**
     * Gets contact.
     *
     * @return the contact
     */
    public String getContact() {
        return contact;
    }

    /**
     * Sets contact.
     *
     * @param contact the contact
     */
    public void setContact(String contact) {
        this.contact = contact;
    }
}
