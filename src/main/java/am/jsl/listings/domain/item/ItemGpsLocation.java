package am.jsl.listings.domain.item;

import am.jsl.listings.domain.BaseEntity;

import java.io.Serializable;

/**
 * The item gps location domain object.
 *
 * @author hamlet
 */
public class ItemGpsLocation extends BaseEntity implements Serializable {

    /**
     * The item id
     */
    private String itemId;


    /**
     * The latitude
     */
    private String latitude;

    /**
     * The longitude
     */
    private String longitude;

    /**
     * The altitude
     */
    private String altitude;

    /**
     * Gets item id.
     *
     * @return the item id
     */
    public String getItemId() {
        return itemId;
    }

    /**
     * Sets item id.
     *
     * @param itemId the item id
     */
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    /**
     * Gets latitude.
     *
     * @return the latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * Sets latitude.
     *
     * @param latitude the latitude
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * Gets longitude.
     *
     * @return the longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * Sets longitude.
     *
     * @param longitude the longitude
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * Gets altitude.
     *
     * @return the altitude
     */
    public String getAltitude() {
        return altitude;
    }

    /**
     * Sets altitude.
     *
     * @param altitude the altitude
     */
    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }
}
