package am.jsl.listings.domain.transaction;

import am.jsl.listings.domain.BaseEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * The transaction domain object.
 *
 * @author hamlet
 */
public class Transaction extends BaseEntity implements Serializable {

    /**
     * The amount
     */
    private double amount;

    /**
     * The status
     * @see TransactionStatus
     */
    private byte status;


    /**
     * The transaction type
     */
    private byte transactionType;

    /**
     * The transaction source
     * @see TransactionSource
     */
    private byte transactionSource = TransactionSource.MANUAL.getValue();

    /**
     * The transaction date
     */
    private Date transactionDate;

    /**
     * The status change date
     */
    private Date statusChangeDate;

    /**
     * The description
     */
    private String description;

    /**
     * The account id
     */
    private long accountId;

    /**
     * Gets amount.
     *
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets amount.
     *
     * @param amount the amount
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public byte getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(byte status) {
        this.status = status;
    }

    /**
     * Gets transaction type.
     *
     * @return the transaction type
     */
    public byte getTransactionType() {
        return transactionType;
    }

    /**
     * Sets transaction type.
     *
     * @param transactionType the transaction type
     */
    public void setTransactionType(byte transactionType) {
        this.transactionType = transactionType;
    }

    /**
     * Gets transaction source.
     *
     * @return the transaction source
     */
    public byte getTransactionSource() {
        return transactionSource;
    }

    /**
     * Sets transaction source.
     *
     * @param transactionSource the transaction source
     */
    public void setTransactionSource(byte transactionSource) {
        this.transactionSource = transactionSource;
    }

    /**
     * Gets transaction date.
     *
     * @return the transaction date
     */
    public Date getTransactionDate() {
        return transactionDate;
    }

    /**
     * Sets transaction date.
     *
     * @param transactionDate the transaction date
     */
    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    /**
     * Gets status change date.
     *
     * @return the status change date
     */
    public Date getStatusChangeDate() {
        return statusChangeDate;
    }

    /**
     * Sets status change date.
     *
     * @param statusChangeDate the status change date
     */
    public void setStatusChangeDate(Date statusChangeDate) {
        this.statusChangeDate = statusChangeDate;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets account id.
     *
     * @return the account id
     */
    public long getAccountId() {
        return accountId;
    }

    /**
     * Sets account id.
     *
     * @param accountId the account id
     */
    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        if (!super.equals(obj)) {
            return false;
        }
        final Transaction other = (Transaction) obj;
        return Objects.equals(this.getId(), other.getId());
    }
}
