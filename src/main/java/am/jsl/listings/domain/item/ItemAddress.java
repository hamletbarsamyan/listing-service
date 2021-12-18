package am.jsl.listings.domain.item;

import am.jsl.listings.domain.BaseEntity;

import java.io.Serializable;

/**
 * The item address domain object.
 *
 * @author hamlet
 */
public class ItemAddress extends BaseEntity implements Serializable {

    /**
     * The item id
     */
    private long itemId;

    /**
     * The country id
     */
    private long countryId;

    /**
     * The state id
     */
    private long stateId;

    /**
     * The city id
     */
    private long cityId;

    /**
     * The district id
     */
    private long districtId;

    /**
     * The address line 1
     */
    private String addressLine1;

    /**
     * The address line 2
     */
    private String addressLine2;

    /**
     * The zip
     */
    private String zip;

    /**
     * The locale
     */
    protected String locale;


    /**
     * Gets item id.
     *
     * @return the item id
     */
    public long getItemId() {
        return itemId;
    }

    /**
     * Sets item id.
     *
     * @param itemId the item id
     */
    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    /**
     * Gets country id.
     *
     * @return the country id
     */
    public long getCountryId() {
        return countryId;
    }

    /**
     * Sets country id.
     *
     * @param countryId the country id
     */
    public void setCountryId(long countryId) {
        this.countryId = countryId;
    }

    /**
     * Gets state id.
     *
     * @return the state id
     */
    public long getStateId() {
        return stateId;
    }

    /**
     * Sets state id.
     *
     * @param stateId the state id
     */
    public void setStateId(long stateId) {
        this.stateId = stateId;
    }

    /**
     * Gets city id.
     *
     * @return the city id
     */
    public long getCityId() {
        return cityId;
    }

    /**
     * Sets city id.
     *
     * @param cityId the city id
     */
    public void setCityId(long cityId) {
        this.cityId = cityId;
    }

    /**
     * Gets district id.
     *
     * @return the district id
     */
    public long getDistrictId() {
        return districtId;
    }

    /**
     * Sets district id.
     *
     * @param districtId the district id
     */
    public void setDistrictId(long districtId) {
        this.districtId = districtId;
    }

    /**
     * Gets address line 1.
     *
     * @return the address line 1
     */
    public String getAddressLine1() {
        return addressLine1;
    }

    /**
     * Sets address line 1.
     *
     * @param addressLine1 the address line 1
     */
    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    /**
     * Gets address line 2.
     *
     * @return the address line 2
     */
    public String getAddressLine2() {
        return addressLine2;
    }

    /**
     * Sets address line 2.
     *
     * @param addressLine2 the address line 2
     */
    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    /**
     * Gets zip.
     *
     * @return the zip
     */
    public String getZip() {
        return zip;
    }

    /**
     * Sets zip.
     *
     * @param zip the zip
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}
