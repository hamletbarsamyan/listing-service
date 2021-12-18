package am.jsl.listings.domain.reminder;

import am.jsl.listings.domain.BaseEntity;
import am.jsl.listings.domain.transaction.TransactionType;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * The reminder domain object.
 * The reminders will be processed by reminder job based on reminder's due date
 * and transaction(s) will be created based this reminder configuration:
 * account, amount, transaction type.
 *
 * @author hamlet
 */
public class Reminder extends BaseEntity implements Serializable {
    /**
     * The transaction type of reminder(expense, income, transfer)
     * @see TransactionType
     */
    private byte transactionType;

    /**
     * The status of reminder
     * @see ReminderStatus
     */
    private byte status;

    /**
     * The due date of reminder
     */
    private LocalDateTime dueDate;

    /**
     * The account id of reminder
     */
    private long accountId;

    /**
     * The amount of this reminder which will be used for creating transactions
     */
    private double amount;

    /**
     * If auto charge is true then a transaction will be created automatically
     */
    private boolean autoCharge;

    /**
     * If repeat is true then this reminder will be processed repeatedly on each reminder job execution
     * @see ReminderRepeat
     */
    private byte repeat;

    /**
     * The description
     */
    private String description;

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
     * Gets due date.
     *
     * @return the due date
     */
    public LocalDateTime getDueDate() {
        return dueDate;
    }

    /**
     * Sets due date.
     *
     * @param dueDate the due date
     */
    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
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
     * Is auto charge boolean.
     *
     * @return the boolean
     */
    public boolean isAutoCharge() {
        return autoCharge;
    }

    /**
     * Sets auto charge.
     *
     * @param autoCharge the auto charge
     */
    public void setAutoCharge(boolean autoCharge) {
        this.autoCharge = autoCharge;
    }

    /**
     * Gets repeat.
     *
     * @return the repeat
     */
    public byte getRepeat() {
        return repeat;
    }

    /**
     * Sets repeat.
     *
     * @param repeat the repeat
     */
    public void setRepeat(byte repeat) {
        this.repeat = repeat;
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
        final Reminder other = (Reminder) obj;
        return Objects.equals(this.getId(), other.getId());
    }
}
