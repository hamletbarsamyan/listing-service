package am.jsl.listings.dto.reminder;

import java.io.Serializable;

/**
 * The ReminderDetailsDTO is used for transferring a reminder detailed information.
 * @author hamlet
 */
public class ReminderDetailsDTO extends BaseReminderDTO implements Serializable {
    /**
     * The account name
     */
    private String account;

    /**
     * The account icon
     */
    private String accountIcon;

    /**
     * The account color
     */
    private String accountColor;

    /**
     * The contact id
     */
    private long contactId;

    /**
     * The contact name
     */
    private String contact;

    /**
     * Target account id for transfer type
     */
    private long targetAccountId;

    /**
     * Target account name for transfer type
     */
    private String targetAccount;

    /**
     * Target account icon for transfer type
     */
    private String targetAccountIcon;

    /**
     * Target account color for transfer type
     */
    private String targetAccountColor;

    /**
     * Target account's currency native symbol for transfer type
     */
    private String targetAccountSymbol;

    /**
     * The exchange rate of currency which is used when calculated the convertedAmount
     */
    private double exchangeRate;

    /**
     * The converted amount
     */
    private double convertedAmount;

    /**
     * If auto charge is true then a transaction will be created automatically
     */
    private boolean autoCharge;

    /**
     * If repeat is true then this reminder will be processed repeatedly on each reminder job execution
     */
    private short repeat;

    /**
     * Getter for property 'account'.
     *
     * @return Value for property 'account'.
     */
    public String getAccount() {
        return account;
    }

    /**
     * Setter for property 'account'.
     *
     * @param account Value to set for property 'account'.
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * Getter for property 'accountIcon'.
     *
     * @return Value for property 'accountIcon'.
     */
    public String getAccountIcon() {
        return accountIcon;
    }

    /**
     * Setter for property 'accountIcon'.
     *
     * @param accountIcon Value to set for property 'accountIcon'.
     */
    public void setAccountIcon(String accountIcon) {
        this.accountIcon = accountIcon;
    }

    /**
     * Getter for property 'accountColor'.
     *
     * @return Value for property 'accountColor'.
     */
    public String getAccountColor() {
        return accountColor;
    }

    /**
     * Setter for property 'accountColor'.
     *
     * @param accountColor Value to set for property 'accountColor'.
     */
    public void setAccountColor(String accountColor) {
        this.accountColor = accountColor;
    }

    /**
     * Getter for property 'contactId'.
     *
     * @return Value for property 'contactId'.
     */
    public long getContactId() {
        return contactId;
    }

    /**
     * Setter for property 'contactId'.
     *
     * @param contactId Value to set for property 'contactId'.
     */
    public void setContactId(long contactId) {
        this.contactId = contactId;
    }

    /**
     * Getter for property 'contact'.
     *
     * @return Value for property 'contact'.
     */
    public String getContact() {
        return contact;
    }

    /**
     * Setter for property 'contact'.
     *
     * @param contact Value to set for property 'contact'.
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * Getter for property 'targetAccountId'.
     *
     * @return Value for property 'targetAccountId'.
     */
    public long getTargetAccountId() {
        return targetAccountId;
    }

    /**
     * Setter for property 'targetAccountId'.
     *
     * @param targetAccountId Value to set for property 'targetAccountId'.
     */
    public void setTargetAccountId(long targetAccountId) {
        this.targetAccountId = targetAccountId;
    }

    /**
     * Getter for property 'targetAccount'.
     *
     * @return Value for property 'targetAccount'.
     */
    public String getTargetAccount() {
        return targetAccount;
    }

    /**
     * Setter for property 'targetAccount'.
     *
     * @param targetAccount Value to set for property 'targetAccount'.
     */
    public void setTargetAccount(String targetAccount) {
        this.targetAccount = targetAccount;
    }

    /**
     * Getter for property 'targetAccountIcon'.
     *
     * @return Value for property 'targetAccountIcon'.
     */
    public String getTargetAccountIcon() {
        return targetAccountIcon;
    }

    /**
     * Setter for property 'targetAccountIcon'.
     *
     * @param targetAccountIcon Value to set for property 'targetAccountIcon'.
     */
    public void setTargetAccountIcon(String targetAccountIcon) {
        this.targetAccountIcon = targetAccountIcon;
    }

    /**
     * Getter for property 'targetAccountColor'.
     *
     * @return Value for property 'targetAccountColor'.
     */
    public String getTargetAccountColor() {
        return targetAccountColor;
    }

    /**
     * Setter for property 'targetAccountColor'.
     *
     * @param targetAccountColor Value to set for property 'targetAccountColor'.
     */
    public void setTargetAccountColor(String targetAccountColor) {
        this.targetAccountColor = targetAccountColor;
    }

    public String getTargetAccountSymbol() {
        return targetAccountSymbol;
    }

    public void setTargetAccountSymbol(String targetAccountSymbol) {
        this.targetAccountSymbol = targetAccountSymbol;
    }

    /**
     * Getter for property 'exchangeRate'.
     *
     * @return Value for property 'exchangeRate'.
     */
    public double getExchangeRate() {
        return exchangeRate;
    }

    /**
     * Setter for property 'exchangeRate'.
     *
     * @param exchangeRate Value to set for property 'exchangeRate'.
     */
    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    /**
     * Getter for property 'convertedAmount'.
     *
     * @return Value for property 'convertedAmount'.
     */
    public double getConvertedAmount() {
        return convertedAmount;
    }

    /**
     * Setter for property 'convertedAmount'.
     *
     * @param convertedAmount Value to set for property 'convertedAmount'.
     */
    public void setConvertedAmount(double convertedAmount) {
        this.convertedAmount = convertedAmount;
    }

    /**
     * Getter for property 'autoCharge'.
     *
     * @return Value for property 'autoCharge'.
     */
    public boolean isAutoCharge() {
        return autoCharge;
    }

    /**
     * Setter for property 'autoCharge'.
     *
     * @param autoCharge Value to set for property 'autoCharge'.
     */
    public void setAutoCharge(boolean autoCharge) {
        this.autoCharge = autoCharge;
    }

    /**
     * Getter for property 'repeat'.
     *
     * @return Value for property 'repeat'.
     */
    public short getRepeat() {
        return repeat;
    }

    /**
     * Setter for property 'repeat'.
     *
     * @param repeat Value to set for property 'repeat'.
     */
    public void setRepeat(short repeat) {
        this.repeat = repeat;
    }
}
