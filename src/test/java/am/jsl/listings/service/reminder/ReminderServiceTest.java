package am.jsl.listings.service.reminder;

import am.jsl.listings.domain.account.Account;
import am.jsl.listings.domain.category.Category;
import am.jsl.listings.domain.reminder.Reminder;
import am.jsl.listings.domain.reminder.ReminderRepeat;
import am.jsl.listings.domain.reminder.ReminderStatus;
import am.jsl.listings.domain.transaction.TransactionType;
import am.jsl.listings.dto.reminder.ReminderListDTO;
import am.jsl.listings.search.ListPaginatedResult;
import am.jsl.listings.search.reminder.ReminderSearchQuery;
import am.jsl.listings.service.BaseTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Contains ReminderService tests.
 */
public class ReminderServiceTest extends BaseTest {

    /**
     * The ReminderService
     */
//    @Autowired
    protected ReminderService reminderService;

    /**
     * Executed before all ReminderServiceTest tests.
     *
     * @throws Exception if failed
     */
//    @BeforeAll
    public void setUp() throws Exception {
        user = createUser();
        category = createCategory();
    }

//    @Test
//    @DisplayName("Create Income Reminder Test")
    public void testCreateIncomeReminder() throws Exception {
        log.info("Starting test for create income reminder");
        double amount = 10;
        Account account = createAccount();
        double accountInitialBalance = account.getBalance();

        // create autocharge reminder with with past due date
        Reminder reminder = createReminder(account, null, TransactionType.INCOME, amount,
                LocalDateTime.now().minusHours(1L));
        assertTrue(reminder.getId() > 0);

        // check if account balance is increased
        account = accountService.get(account.getId());
        assertEquals(
                accountInitialBalance + amount, account.getBalance(), 0, "Account balance is not increased");

        log.info("Finished test for create income reminder");
    }

//    @Test
//    @DisplayName("Create Expense Reminder Test")
    public void testCreateExpenseReminder() throws Exception {
        log.info("Starting test for create expense reminder");
        double amount = 10;
        Account account = createAccount();
        double accountInitialBalance = account.getBalance();

        // create autocharge reminder with with past due date
        Reminder reminder = createReminder(account, null, TransactionType.EXPENSE, amount,
                LocalDateTime.now().minusHours(1L));
        assertTrue(reminder.getId() > 0);

        // check if account balance is decreased
        account = accountService.get(account.getId());
        assertEquals(
                accountInitialBalance - amount, account.getBalance(), 0, "Account balance is not decreased");

        log.info("Finished test for create expense reminder");
    }

//    @Test
//    @DisplayName("Create Transfer Reminder Test")
    public void testCreateTransferReminder() throws Exception {
        log.info("Starting test for create transfer reminder");
        double amount = 10;
        Account account = createAccount();
        Account targetAccount = createAccount();
        double accountInitialBalance = account.getBalance();
        double targetAccountInitialBalance = targetAccount.getBalance();

        // create autocharge reminder with with past due date
        Reminder reminder = createReminder(account, targetAccount, TransactionType.TRANSFER, amount,
                LocalDateTime.now().minusMinutes(1L));
        assertTrue(reminder.getId() > 0);

        // check if account balance is decreased
        account = accountService.get(account.getId());
        assertEquals(
                accountInitialBalance - amount, account.getBalance(), 0, "Account balance is not decreased");

        // check if target account balance is increased
        targetAccount = accountService.get(targetAccount.getId());
        assertEquals(
                targetAccountInitialBalance + amount, targetAccount.getBalance(), 0, "Account balance is not increased");

        log.info("Finished test for create transfer reminder");
    }

//    @Test
//    @DisplayName("Update Reminder Test")
    public void testUpdateReminder() throws Exception {
        log.info("Starting test for update reminder");
        Account account = createAccount();
        double accountInitialBalance = account.getBalance();
        double amount = 10;

        // create autocharge reminder with with past due date
        Reminder reminder = createReminder(account, null, TransactionType.INCOME, amount,
                LocalDateTime.now().minusMinutes(1L));
        assertTrue(reminder.getId() > 0);

        // check if account balance is increased
        account = accountService.get(account.getId());
        assertEquals(
                accountInitialBalance + amount, account.getBalance(), 0, "Account balance is not increased");

        Account newAccount = createAccount();
        Category newCategory = createCategory();
        double newAmount = 20;
        byte newTransactionType = TransactionType.EXPENSE.getValue();
        byte newRepeat = ReminderRepeat.YEARLY.getValue();
        boolean newAutoCharge = false;

        String description = "description updated";

        reminder.setAccountId(newAccount.getId());
//        reminder.setCategoryId(newCategory.getId());
        reminder.setAmount(newAmount);
        reminder.setTransactionType(newTransactionType);
        reminder.setRepeat(newRepeat);
        reminder.setAutoCharge(newAutoCharge);
        reminder.setDescription(description);
        reminderService.update(reminder);

        // validate reminder
//        reminder = reminderService.get(reminder.getId(), reminder.getUserId());

        assertEquals(newAccount.getId(), reminder.getAccountId());
//        assertEquals(newCategory.getId(), reminder.getCategoryId());
//        assertEquals(newContact.getId(), reminder.getContactId());
        assertEquals(newAmount, reminder.getAmount(), 0);
        assertEquals(newTransactionType, reminder.getTransactionType());
        assertEquals(newAutoCharge, reminder.isAutoCharge());
        assertEquals(newRepeat, reminder.getRepeat());
        assertEquals(description, reminder.getDescription());

        log.info("Finished test for update reminder");
    }

//    @Test
//    @DisplayName("Delete Reminder Test")
    public void testDeleteReminder() throws Exception {
        log.info("Starting test for delete reminder");
        Account account = createAccount();
        double accountInitialBalance = account.getBalance();

        // create autocharge reminder with with past due date
        Reminder reminder = createReminder(account, null, TransactionType.INCOME, 10,
                LocalDateTime.now().minusMinutes(1L));
        long reminderId = reminder.getId();
//        long userId = reminder.getUserId();
//
//        reminderService.delete(reminder.getId(), reminder.getUserId());

        // validate reminder
//        reminder = reminderService.get(reminderId, userId);
        assertNull(reminder);

        log.info("Finished test for delete reminder");
    }

//    @Test
//    @DisplayName("Search Reminders Test")
    public void testSearchReminders() throws Exception {
        log.info("Starting test for search reminders");
        Account account = createAccount();

        // create reminders
        createReminder(account, null, TransactionType.INCOME, 10,
                LocalDateTime.now());
        createReminder(account, null, TransactionType.EXPENSE, 10,
                LocalDateTime.now());

        ReminderSearchQuery query = new ReminderSearchQuery(1, 10);
        query.setUserId(user.getId());
        query.setTransactionType(TransactionType.INCOME.getValue());

        ListPaginatedResult<ReminderListDTO> result = reminderService.search(query);
        assertEquals( 1, result.getTotal(), "Incorrect reminder search result");

        query.setTransactionType(TransactionType.EXPENSE.getValue());

        result = reminderService.search(query);
        assertEquals( 1, result.getTotal(), "Incorrect reminder search result");

        log.info("Finished test for search reminders");
    }

    /**
     * Creates a new Reminder with the given fields.
     *
     * @param account         the account
     * @param targetAccount   the target account
     * @param transactionType the transaction type
     * @param amount          the reminder amount
     * @return the Reminder
     * @throws Exception if error occurs
     */
    private Reminder createReminder(Account account, Account targetAccount, TransactionType transactionType, double amount, LocalDateTime dueDate)
            throws Exception {
        Reminder reminder = new Reminder();
        reminder.setStatus(ReminderStatus.ACTIVE.getValue());
        reminder.setAccountId(account.getId());
//        reminder.setCategoryId(category.getId());
//        reminder.setContactId(contact.getId());
        reminder.setAmount(amount);
        reminder.setTransactionType(transactionType.getValue());
        reminder.setDescription("description");
//        reminder.setUserId(user.getId());
        reminder.setAutoCharge(true);
        reminder.setDueDate(dueDate);

        reminderService.create(reminder);

        return reminder;
    }

    /**
     * Executed after all ReminderServiceTest tests.
     *
     * @throws Exception if failed
     */
//    @AfterAll
    public void cleanUp() throws Exception {
        super.cleanUp();
    }
}