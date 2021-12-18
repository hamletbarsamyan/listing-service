package am.jsl.listings.dao.reminder;

import am.jsl.listings.dao.BaseDao;
import am.jsl.listings.domain.reminder.Reminder;
import am.jsl.listings.dto.reminder.ReminderDetailsDTO;
import am.jsl.listings.dto.reminder.ReminderListDTO;
import am.jsl.listings.search.ListPaginatedResult;
import am.jsl.listings.search.reminder.ReminderSearchQuery;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The Dao interface for accessing {@link Reminder} domain object.
 * @author hamlet
 */
public interface ReminderDao extends BaseDao<Reminder> {
    /**
     * Retrieves paginated result for the given search query.
     * @param searchQuery the {@link ReminderSearchQuery} containing query options
     * @return the {@link ListPaginatedResult} containing paged result
     */
    ListPaginatedResult<ReminderListDTO> search(ReminderSearchQuery searchQuery);

    /**
     * Returns a reminder details with the given id and user id.
     * @param id the reminder id
     * @param userId the user id
     * @return the reminder details
     */
    ReminderDetailsDTO getDetails(long id, long userId);

    /**
     *  Returns due reminders for the given user id.
     * @param userId the user id
     * @return the list of due reminders
     */
    List<ReminderListDTO> getDueReminders(long userId);

    /**
     * Updates the given the due date of the given reminder.
     * @param id the reminder id
     * @param dueDate the due date
     */
    void updateReminderDue(long id, LocalDateTime dueDate);

    /**
     * Creates reminder / transaction mapping with the given reminder id and transaction id.
     * @param reminderId the reminder id
     * @param transactionId the transaction id
     */
    void createReminderTransaction(long reminderId, long transactionId);
}
