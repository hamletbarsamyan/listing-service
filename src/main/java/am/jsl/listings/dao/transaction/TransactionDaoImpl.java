package am.jsl.listings.dao.transaction;

import am.jsl.listings.dao.BaseDaoImpl;
import am.jsl.listings.dao.DBUtils;
import am.jsl.listings.dao.transaction.mapper.*;
import am.jsl.listings.domain.transaction.Transaction;
import am.jsl.listings.dto.transaction.*;
import am.jsl.listings.search.ListPaginatedResult;
import am.jsl.listings.search.transaction.TransactionByCategorySearchQuery;
import am.jsl.listings.search.transaction.TransactionSearchQuery;
import am.jsl.listings.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The implementation of Dao interface for accessing {@link Transaction} domain object.
 * @author hamlet
 */
@Repository("transactionDao")
@Lazy
public class TransactionDaoImpl extends BaseDaoImpl<Transaction> implements TransactionDao {
    private TransactionMapper transactionMapper = new TransactionMapper();
    private TransactionListDTOMapper listDTOMapper = new TransactionListDTOMapper();
    private TransactionDetailsMapper detailsMapper = new TransactionDetailsMapper();
    private TransactionListTotalMapper totalMapper = new TransactionListTotalMapper();
    private TransactionByCategoryDTOMapper trByCategoryDTOMapper = new TransactionByCategoryDTOMapper();

    private static final Map<String, String> sortByColumnMap = new HashMap<>();

    static {
        sortByColumnMap.put("type", "t.transaction_type AAA, t.id ");
        sortByColumnMap.put("date", "t.transaction_date AAA, t.id ");
        sortByColumnMap.put("cat", "pcat.name, cat.name AAA, t.id ");
        sortByColumnMap.put("amount", "t.amount AAA, t.id ");
    }

    @Autowired
    public TransactionDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    private static final String searchCommonSql = "select {columns} "
            + "from transaction t "
            + "inner join account acc on acc.id = t.account_id "
            + "inner join category cat on cat.id = t.category_id "
            + "left join category pcat on pcat.id = cat.parent_id "
            + "where 1=1 ";
    private static final String columnsSql = "distinct t.id, acc.symbol," +
            "cat.name as category, cat.icon as category_icon, cat.color as category_color, " +
            "pcat.name as parent_category, pcat.icon as parent_category_icon, pcat.color as parent_category_color, " +
            "t.amount, t.transaction_type, t.transaction_date ";

    @Override
    public TransactionSearchResult search(TransactionSearchQuery searchQuery) {
        TransactionSearchResult result = new TransactionSearchResult();

        int rowsPerPage = searchQuery.getPageSize();
        int pageNum = searchQuery.getPage();
        int offset = (pageNum - 1) * rowsPerPage;

        ListPaginatedResult<TransactionListDTO> pagingResult = new ListPaginatedResult<>();
        Map<String, Object> params = new HashMap<String, Object>();
        String whereClause = createWhereClouse(searchQuery, params);

        String searchSql = searchCommonSql;
        searchSql = searchSql.replace("{columns}", columnsSql);

        StringBuilder limit = new StringBuilder();
        limit.append(" limit ").append(offset);
        limit.append(", ").append(rowsPerPage);

        StringBuilder finalSql = new StringBuilder();
        finalSql.append(searchSql);
        finalSql.append(whereClause);
        finalSql.append(createOrderByClause(searchQuery));
        finalSql.append(limit.toString());

        // search
        List<TransactionListDTO> list = parameterJdbcTemplate.query(
                finalSql.toString(), params, listDTOMapper);

        searchSql = searchCommonSql;
        searchSql = searchSql.replace("{columns}", "count(t.id) as cnt");

        finalSql = new StringBuilder();
        finalSql.append(searchSql);
        finalSql.append(whereClause);

        // count
        long count = parameterJdbcTemplate.queryForObject(finalSql.toString(),
                params, Long.class);

        pagingResult.setTotal(count);
        pagingResult.setList(list);

        // calculate totals
        if (count > 0 && searchQuery.isCalculateTotals()) {
            result.setTotals(calculateSearchTotals(params, whereClause));
        }

        result.setListPaginatedResult(pagingResult);
        return result;
    }

    private static final String totalStartSql = "select * from (";
    private static final String totalColumnsSql = "t.transaction_type, sum(t.amount) as total, acc.symbol ";
    private static final String totalEndSql = " group by t.transaction_type, acc.currency, acc.symbol) res " +
            "where res.total > 0 order by res.transaction_type, res.symbol";

    private List<TransactionListTotalDTO> calculateSearchTotals(Map<String, Object> params, String whereClause) {
        String searchSql = searchCommonSql;
        searchSql = searchSql.replace("{columns}", totalColumnsSql);

        StringBuilder finalSql = new StringBuilder();
        finalSql.append(totalStartSql);
        finalSql.append(searchSql);
        finalSql.append(whereClause);
        finalSql.append(totalEndSql);

        return parameterJdbcTemplate.query(
                finalSql.toString(), params, totalMapper);
    }

    private String createWhereClouse(TransactionSearchQuery searchQuery,
                                     Map<String, Object> params) {
        StringBuilder where = new StringBuilder();

        where.append(" and t.user_id = :userId");
        params.put("userId", searchQuery.getUserId());

        if (searchQuery.getAccountId() != 0) {
            where.append(" and t.account_id = :account_id");
            params.put("account_id", searchQuery.getAccountId());
        }

        if (searchQuery.getCategoryId() != 0) {
            where.append(" and (t.category_id = :category_id or cat.parent_id = :category_id) ");
            params.put("category_id", searchQuery.getCategoryId());
        }

        if (searchQuery.getTransactionType() != 0) {
            where.append(" and t.transaction_type = :transactionType");
            params.put("transactionType", searchQuery.getTransactionType());
        }

        if (searchQuery.getTransactionSource() != 0) {
            where.append(" and t.transaction_source = :transactionSource");
            params.put("transactionSource", searchQuery.getTransactionSource());
        }

        if (searchQuery.getStartDate() != null) {
            where.append(" and DATE(t.transaction_date) >= :startDate");
            params.put("startDate", searchQuery.getStartDate());
        }
        if (searchQuery.getEndDate() != null) {
            where.append(" and DATE(t.transaction_date) <= :endDate");
            params.put("endDate", searchQuery.getEndDate());
        }

        if (searchQuery.getContact() != 0) {
            where.append(" and t.contact_id = :contact_id");
            params.put("contact_id", searchQuery.getContact());
        }

        if (!TextUtils.isEmpty(searchQuery.getDescription())) {
            params.put("description", "%" + searchQuery.getDescription() + "%");
        }

        return where.toString();
    }

    private String createOrderByClause(TransactionSearchQuery searchQuery) {
        String sortBy = searchQuery.getSortBy();
        String result = "";

        if (TextUtils.hasText(sortBy)) {
            String sortByCol = sortByColumnMap.get(sortBy);

            if (TextUtils.hasText(sortByCol)) {
                result += " order by " + sortByCol;

                if (searchQuery.isAsc()) {
                    result = result.replaceAll("AAA", "");
                } else {
                    result = result.replaceAll("AAA", "desc");
                }
            }
        }

        return result;
    }

    private static final String deleteSql = "delete from transaction where user_id = :user_id and id = :id";

    @Override
    public void delete(long id) {
        Map<String, Object> params = new HashMap<>();
        params.put(DBUtils.id, id);
        delete(id, deleteSql);
    }

    private static final String createSql = "insert into transaction "
            + "(id, account_id, category_id, amount, status, " +
            "transaction_type, transaction_source, transaction_date, contact_id, description, user_id) "
            + "values(:id, :account_id, :category_id, :amount, :status, " +
            ":transaction_type, :transaction_source, :transaction_date, :contact_id, :description, :user_id)";

    @Override
    public void create(Transaction transaction) {
        long id = DBUtils.getNextId(getJdbcTemplate(), "transaction");
        transaction.setId(id);
        Map<String, Object> params = new HashMap<>();
        params.put(DBUtils.id, transaction.getId());

        params.put(DBUtils.account_id, transaction.getAccountId());
        params.put(DBUtils.amount, transaction.getAmount());
        params.put(DBUtils.status, transaction.getStatus());
        params.put(DBUtils.transaction_type, transaction.getTransactionType());
        params.put(DBUtils.transaction_source, transaction.getTransactionSource());
        params.put(DBUtils.transaction_date, transaction.getTransactionDate());
        params.put(DBUtils.description, transaction.getDescription());

        parameterJdbcTemplate.update(createSql, params);
    }

    private static final String createBatchSql = "insert into transaction "
            + "(id,  account_id, category_id, amount, status, " +
            "transaction_type, transaction_source, transaction_date, contact_id, description, user_id) "
            + "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    @Override
    public void createBatch(List<Transaction> transactions) {
        final long[] id = {DBUtils.getNextId(getJdbcTemplate(), "transaction")};

        getJdbcTemplate().batchUpdate(createBatchSql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                id[0]++;
                Transaction transaction = transactions.get(i);
                transaction.setId(id[0]);
                ps.setLong(1, id[0]);
                ps.setLong(2, transaction.getAccountId());
                ps.setDouble(4, transaction.getAmount());
                ps.setByte(5, transaction.getStatus());
                ps.setByte(6, transaction.getTransactionType());
                ps.setByte(7, transaction.getTransactionSource());
//                ps.setDate(8, transaction.getTransactionDate());
                ps.setString(10, transaction.getDescription());
            }

            @Override
            public int getBatchSize() {
                return transactions.size();
            }
        });
    }

    private static final String getDetailsSql = "select distinct t.id, t.amount, "
            + "t.transaction_type, t.transaction_date, "
            + "acc.name as account, acc.icon as account_icon, acc.color as account_color, acc.symbol, "
            + "cat.name as category, cat.icon as category_icon, cat.color as category_color, "
            + "pcat.name as parent_category, pcat.icon as parent_category_icon, pcat.color as parent_category_color, "
            + "ct.name as contact, t.description "
            + "from transaction t "
            + "inner join account acc on acc.id = t.account_id "
            + "inner join category cat on cat.id = t.category_id "
            + "left join category pcat on pcat.id = cat.parent_id "
            + "left join contact ct on ct.id = t.contact_id "
            + "where t.id = :id and t.user_id = :user_id";

    @Override
    public TransactionDetailsDTO getDetails(long id, long userId) {
        Map<String, Object> params = new HashMap<>();
        params.put(DBUtils.id, id);
        params.put(DBUtils.user_id, userId);

        TransactionDetailsDTO detailsDTO = parameterJdbcTemplate.queryForObject(getDetailsSql, params, detailsMapper);

        return detailsDTO;
    }

    private static final String getReminderTransactionsSql = "select distinct t.id, acc.symbol,"
            + "cat.name as category, cat.icon as category_icon, cat.color as category_color, "
            + "pcat.name as parent_category, pcat.icon as parent_category_icon, pcat.color as parent_category_color, "
            + "t.amount, t.transaction_type, t.transaction_date "
            + "from transaction t "
            + "inner join reminder_transaction rem on rem.transaction_id = t.id "
            + "inner join account acc on acc.id = t.account_id "
            + "inner join category cat on cat.id = t.category_id "
            + "left join category pcat on pcat.id = cat.parent_id "
            + "where rem.reminder_id = :reminder_id and t.user_id = :user_id " +
            "order by t.transaction_date desc";

    @Override
    public List<TransactionListDTO> getReminderTransactions(long reminderId, long userId) {
        Map<String, Object> params = new HashMap<>();
        params.put(DBUtils.reminder_id, reminderId);
        params.put(DBUtils.user_id, userId);

        return parameterJdbcTemplate.query(getReminderTransactionsSql, params, listDTOMapper);
    }

    private static final String searchByCategorySql = "select * from (select cat.name as category, " +
            "cat.icon as category_icon, cat.color as category_color, " +
            "pcat.name as parent_category, pcat.icon as parent_category_icon, " +
            "pcat.color as parent_category_color, acc.symbol, sum(t.amount) as total "
            + "from transaction t "
            + "inner join account acc on acc.id = t.account_id "
            + "inner join category cat on cat.id = t.category_id "
            + "left join category pcat on pcat.id = cat.parent_id "
            + "where 1=1 ";
    @Override
    public List<TransactionByCategoryDTO> search(TransactionByCategorySearchQuery query) {
        Map<String, Object> params = new HashMap<>();

        StringBuilder finalSql = new StringBuilder();
        finalSql.append(searchByCategorySql);

        finalSql.append(" and t.user_id = :userId");
        params.put("userId", query.getUserId());

        finalSql.append(" and t.account_id = :account_id");
        params.put("account_id", query.getAccountId());

        finalSql.append(" and t.transaction_type = :transactionType");
        params.put("transactionType", query.getTransactionType());

        if (query.getStartDate() != null) {
            finalSql.append(" and DATE(t.transaction_date) >= :startDate");
            params.put("startDate", query.getStartDate());
        }
        if (query.getEndDate() != null) {
            finalSql.append(" and DATE(t.transaction_date) <= :endDate");
            params.put("endDate", query.getEndDate());
        }

        finalSql.append(" group by t.category_id) as t where t.total > 0 order by t.total desc");

        return parameterJdbcTemplate.query(
                finalSql.toString(), params, trByCategoryDTOMapper);
    }

    private static final String getAmountSql = "select amount from transaction where id = :id and user_id = :user_id";

    @Override
    public double getAmount(long id, long userId) {
        Map<String, Object> params = new HashMap<>();
        params.put(DBUtils.id, id);
        params.put(DBUtils.user_id, userId);
        return parameterJdbcTemplate.queryForObject(getAmountSql, params, Double.class);
    }

    private static final String updateSql = "update transaction "
            + "set account_id = :account_id, category_id = :category_id, amount = :amount, status = :status, "
            + "transaction_type = :transaction_type, transaction_date = :transaction_date, contact_id = :contact_id, description = :description "
            + "where user_id = :user_id and id = :id";

    @Override
    public void update(Transaction transaction) {
        long transactionId = transaction.getId();


        Map<String, Object> params = new HashMap<>();
        params.put(DBUtils.id, transactionId);
        params.put(DBUtils.account_id, transaction.getAccountId());
        params.put(DBUtils.amount, transaction.getAmount());
        params.put(DBUtils.status, transaction.getStatus());
        params.put(DBUtils.transaction_type, transaction.getTransactionType());
        params.put(DBUtils.transaction_date, transaction.getTransactionDate());
        params.put(DBUtils.description, transaction.getDescription());

        parameterJdbcTemplate.update(updateSql, params);
    }
    private static final String getSql = "select * from transaction where user_id = :user_id and id = :id";

    @Override
    public Transaction get(long id) {
        Transaction transaction = get(id, getSql, transactionMapper);

        if (transaction == null) {
            return null;
        }

        return transaction;
    }
}
