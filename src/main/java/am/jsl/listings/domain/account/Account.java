package am.jsl.listings.domain.account;


import am.jsl.listings.domain.BaseEntity;

import java.io.Serializable;

/**
 * The account domain object.
 *
 * @author hamlet
 */
public class Account extends BaseEntity implements Serializable {
    /**
     * The current balance of this account
     */
    private double balance;

    /**
     *  The ISO 4217 currency code of this account
     */
    private String currency;

    /**
     * The currency symbol of this account
     */
    private String symbol;

    /**
     * The user id
     */
    private long userId;

    /**
     * Gets balance.
     *
     * @return the balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Sets balance.
     *
     * @param balance the balance
     */
    public void setBalance(double balance) {
        this.balance = balance;
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
     * Gets symbol.
     *
     * @return the symbol
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Sets symbol.
     *
     * @param symbol the symbol
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

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
}
