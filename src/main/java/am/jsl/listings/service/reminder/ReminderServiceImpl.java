package am.jsl.listings.service.reminder;

import am.jsl.listings.dao.account.AccountDao;
import am.jsl.listings.dao.reminder.ReminderDao;
import am.jsl.listings.domain.reminder.Reminder;
import am.jsl.listings.domain.reminder.ReminderRepeat;
import am.jsl.listings.domain.reminder.ReminderStatus;
import am.jsl.listings.domain.transaction.Transaction;
import am.jsl.listings.domain.transaction.TransactionSource;
import am.jsl.listings.domain.transaction.TransactionStatus;
import am.jsl.listings.dto.reminder.ReminderDetailsDTO;
import am.jsl.listings.dto.reminder.ReminderListDTO;
import am.jsl.listings.ex.CannotDeleteException;
import am.jsl.listings.search.ListPaginatedResult;
import am.jsl.listings.search.reminder.ReminderSearchQuery;
import am.jsl.listings.service.BaseServiceImpl;
import am.jsl.listings.service.transaction.TransactionService;
import am.jsl.listings.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The service implementation of the {@link ReminderService}.
 * @author hamlet
 */
@Service("reminderService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class ReminderServiceImpl extends BaseServiceImpl<Reminder> implements ReminderService {

    /**
     * The template file where is stored a html representation of expired reminders.
     */
    private static final String REMINDER_ALERT_HTML = "reminder-alert.html";

    /**
     * The reminder dao.
     */
    private ReminderDao reminderDao;

    /**
     * The account dao.
     */
    private AccountDao accountDao;

    /**
     * The transaction service
     */
    @Autowired
    private TransactionService transactionService;

    /**
     * The reminder alert builder.
     */
    @Autowired
    private ReminderAlertBuilder reminderAlertBuilder;

    @Override
    public ListPaginatedResult<ReminderListDTO> search(ReminderSearchQuery searchQuery) {
        return reminderDao.search(searchQuery);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void create(Reminder reminder) {
        if (reminder.getDueDate() == null) {
            reminder.setDueDate(getDueDate(LocalDateTime.now(), reminder.getRepeat()));
        }

        reminderDao.create(reminder);

//        Map<Long, List<ReminderListDTO>> userReminderMap = applyReminders(reminder.getUserId());
//        publish(reminder.getUserId(), userReminderMap.get(reminder.getUserId()));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void update(Reminder reminder) {
        reminderDao.update(reminder);

//        Map<Long, List<ReminderListDTO>> userReminderMap = applyReminders(reminder.getUserId());
//        publish(reminder.getUserId(), userReminderMap.get(reminder.getUserId()));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void delete(long id) throws CannotDeleteException {
        if (!reminderDao.canDelete(id)) {
            throw new CannotDeleteException();
        }
//        reminderDao.delete(id, userId);
//        Map<Long, List<ReminderListDTO>> userReminderMap = applyReminders(userId);
//        publish(userId, userReminderMap.get(userId));
    }

    @Override
    public ReminderDetailsDTO getDetails(long id, long userId) {
        ReminderDetailsDTO detailsDTO = reminderDao.getDetails(id, userId);

        return detailsDTO;
    }

    @Override
    public LocalDateTime getDueDate(LocalDateTime reminderDate, byte repeat) {
        ReminderRepeat reminderRepeat = ReminderRepeat.get(repeat);

        switch (reminderRepeat) {
            case NONE:
                return reminderDate;
            case DAILY:
                return reminderDate.plusDays(1);
            case MONTHLY:
                return reminderDate.plusMonths(1);
            case YEARLY:
                return reminderDate.plusYears(1);
        }

        return null;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void processDueReminders() {
        Map<Long, List<ReminderListDTO>> userReminderMap = applyReminders(0);

        // publish reminders
        if (!userReminderMap.isEmpty()) {
            userReminderMap.keySet().forEach(remUserId -> publish(remUserId, userReminderMap.get(remUserId)));
        }
    }

    /**
     * Processes expired reminders and returns map of user id / due reminders.
     * If reminder is set to auto charge then the appropriate transaction will be created.
     * @param userId the user id
     * @return map of user id / reminders
     */
    private Map<Long, List<ReminderListDTO>> applyReminders(long userId) {
        List<ReminderListDTO> userReminders;
        Map<Long, List<ReminderListDTO>> userReminderMap = new HashMap<>();
        List<ReminderListDTO> reminders = reminderDao.getDueReminders(userId);
        LocalDateTime now = LocalDateTime.now();

        // process reminders
        for (ReminderListDTO reminder : reminders) {
            long remUserId = reminder.getUserId();
            userReminders = userReminderMap.computeIfAbsent(remUserId, k -> new ArrayList<>());
            userReminders.add(reminder);

            if (reminder.isAutoCharge()) { // auto charge
                try {
                    createTransaction(get(reminder.getId()), now);

                    LocalDateTime dueDate = DateUtils.toLocalDateTime(reminder.getDueDate());
                    reminderDao.updateReminderDue(reminder.getId(), getDueDate(dueDate, reminder.getRepeat()));
                    reminder.setStatus(ReminderStatus.DONE.getValue());
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        }

        return userReminderMap;
    }

    /**
     * Creates a new transaction and attaches to the reminder.
     * @param reminder the reminder
     * @param dateTime the date time
     * @throws Exception will be thrown if error occurs
     */
    private void createTransaction(Reminder reminder, LocalDateTime dateTime) throws Exception {
        Transaction transaction = new Transaction();
        transaction.setAccountId(reminder.getAccountId());
        transaction.setAmount(reminder.getAmount());
        transaction.setStatus(TransactionStatus.DONE.getValue());
        transaction.setTransactionType(reminder.getTransactionType());
        transaction.setTransactionSource(TransactionSource.REMINDER.getValue());
//        transaction.setTransactionDate(dateTime);


        transactionService.create(transaction);
        reminderDao.createReminderTransaction(reminder.getId(), transaction.getId());
    }

    /**
     * Generates a html representation of the given reminders for the given user id.
     * @param userId the user id
     * @param reminders the list of reminders
     */
    private void publish(long userId, List<ReminderListDTO> reminders) {
        if (!publishHtml) {
            log.info("Publish html is disabled");
            return;
        }

        String html = reminderAlertBuilder.build(reminders);
        publish(userId, REMINDER_ALERT_HTML, html);
    }

    /**
     * Autowires the given daos
     * @param reminderDao the ReminderDao
     * @param accountDao the AccountDao
     */
    @Autowired
    public void setDaos(@Qualifier("reminderDao") ReminderDao reminderDao,
                        @Qualifier("accountDao") AccountDao accountDao) {
        this.reminderDao = reminderDao;
        this.accountDao = accountDao;
        setBaseDao(reminderDao);
    }
}
