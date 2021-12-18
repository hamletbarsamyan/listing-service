package am.jsl.listings.dto.item;

import am.jsl.listings.dto.NamedDTO;

import java.io.Serializable;
import java.util.Date;

/**
 * Contains item common data.
 *
 * @author hamlet
 */
public class BaseItemDTO extends NamedDTO implements Serializable {

    /**
     * The price of this item
     */
    protected double price;

    /**
     * The currency code
     */
    protected String currency;

    /**
     * The Universal Product Code
     */
    protected String upc;

    /**
     * Flag indicating the status of this item
     */
    protected boolean active = true;

    /**
     * The creation date
     */
    protected Date createdAt;

    /**
     * Gets price.
     *
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets price.
     *
     * @param price the price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets currency.
     *
     * @return the currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Sets currency.
     *
     * @param currency the currency
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * Gets upc.
     *
     * @return the upc
     */
    public String getUpc() {
        return upc;
    }

    /**
     * Sets upc.
     *
     * @param upc the upc
     */
    public void setUpc(String upc) {
        this.upc = upc;
    }

    /**
     * Is active boolean.
     *
     * @return the boolean
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets active.
     *
     * @param active the active
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Gets created at.
     *
     * @return the created at
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets created at.
     *
     * @param createdAt the created at
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
