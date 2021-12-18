package am.jsl.listings.service.reminder;

import am.jsl.listings.domain.reminder.Reminder;
import am.jsl.listings.dto.reminder.ReminderDetailsDTO;
import am.jsl.listings.dto.reminder.ReminderListDTO;
import am.jsl.listings.search.ListPaginatedResult;
import am.jsl.listings.search.reminder.ReminderSearchQuery;
import am.jsl.listings.service.BaseService;

import java.time.LocalDateTime;

/**
 * Service interface which defines all the methods for working with {@link Reminder} domain object.
 * @author hamlet
 */
public interface ReminderService extends BaseService<Reminder> {
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
     * Returns the next due date for the given reminderDate and repeat option.
     * @param reminderDate the remidner date
     * @param repeat the {@link am.jsl.listings.domain.reminder.ReminderRepeat} option
     * @return the next due date
     */
    LocalDateTime getDueDate(LocalDateTime reminderDate, byte repeat);

    /**
     * Processes reminders with expired due dates.
     * Selects expired reminders for all active users and
     * generates html representation of due reminders in user folders.
     * Appropriate transaction will be generated  based on reminder configuration.
     */
    void processDueReminders();
}
