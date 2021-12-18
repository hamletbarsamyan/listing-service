package am.jsl.listings.dao.item;


import am.jsl.listings.dao.DBUtils;
import am.jsl.listings.dao.MultiLingualDaoImpl;
import am.jsl.listings.dao.item.mapper.*;
import am.jsl.listings.domain.DescriptiveTranslation;
import am.jsl.listings.domain.Translation;
import am.jsl.listings.domain.item.Item;
import am.jsl.listings.dto.Pair;
import am.jsl.listings.dto.item.ItemListDTO;
import am.jsl.listings.dto.item.ItemViewDTO;
import am.jsl.listings.search.ListPaginatedResult;
import am.jsl.listings.search.item.ItemSearchQuery;
import am.jsl.listings.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The implementation of Dao interface for accessing {@link Item} domain object.
 *
 * @author hamlet
 */
@Repository("itemDao")
@Lazy
public class ItemDaoImpl extends MultiLingualDaoImpl<Item> implements ItemDao {
    private static final Map<String, String> sortByColumnMap = new HashMap<>();

    static {
        sortByColumnMap.put("category", "i.category_id AAA, i.id ");
        sortByColumnMap.put("name", "tr.name AAA, i.id ");
        sortByColumnMap.put("price", "i.price AAA, i.id ");
        sortByColumnMap.put("upc", "i.upc AAA, i.id ");
        sortByColumnMap.put("status", "i.active AAA, i.id ");
        sortByColumnMap.put("createdAt", "i.created_at AAA, i.id ");
    }

    private ItemMapper itemMapper = new ItemMapper();
    private ItemViewDTOMapper itemViewDTOMapper = new ItemViewDTOMapper();
    private ItemByLocaleMapper itemByLocaleMapper = new ItemByLocaleMapper();
    private ItemTranslationMapper translationMapper = new ItemTranslationMapper();
    private CategoryItemsCountExtractor categoryItemsCountExtractor = new CategoryItemsCountExtractor();
    @Autowired
    private ItemListDTOExtractor itemListDTOExtractor;

    @Autowired
    public ItemDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    private static final String deleteSql = "delete from item where id = :id";

    @Override
    public void delete(long id) {
        delete(id, deleteSql);
    }

    private static final String createSql = "insert into item "
            + "(id, category_id, listing_type, price, currency, purchase_currency, purchase_price, inv_count, " +
            "upc, sku, active, created_at, changed_at, user_id) "
            + "values(:id, :category_id, :listing_type, :price, :currency, :purchase_currency, :purchase_price, " +
            ":inv_count, :upc, :sku, :active, :created_at, :changed_at, :user_id)";

    @Override
    public void create(Item item) {
        long id = DBUtils.getNextId(getJdbcTemplate(), "item");
        item.setId(id);
        Map<String, Object> params = new HashMap<>(14);
        params.put(DBUtils.id, item.getId());
        params.put(DBUtils.category_id, item.getCategoryId());
        params.put(DBUtils.listing_type, item.getListingType());
        params.put(DBUtils.price, item.getPrice());
        params.put(DBUtils.currency, item.getCurrency());
        params.put(DBUtils.purchase_currency, item.getPurchaseCurrency());
        params.put(DBUtils.purchase_price, item.getPurchasePrice());
        params.put(DBUtils.inv_count, item.getInvCount());
        params.put(DBUtils.upc, item.getUpc());
        params.put(DBUtils.sku, item.getSku());
        params.put(DBUtils.active, item.isActive());
        params.put(DBUtils.created_at, item.getCreatedAt());
        params.put(DBUtils.changed_at, item.getChangedAt());
        params.put(DBUtils.user_id, item.getUserId());
        parameterJdbcTemplate.update(createSql, params);
    }

    private static final String updateSql = "update item "
            + "set category_id = :category_id, listing_type = :listing_type, "
            + "price = :price, currency = :currency, purchase_currency = :purchase_currency, purchase_price = :purchase_price, " +
            "inv_count = :inv_count, upc =:upc, sku = :sku, active = :active, changed_at = :changed_at "
            + "where id = :id and user_id = :user_id";

    @Override
    public void update(Item item) {
        Map<String, Object> params = new HashMap<>(13);
        params.put(DBUtils.id, item.getId());
        params.put(DBUtils.category_id, item.getCategoryId());
        params.put(DBUtils.listing_type, item.getListingType());
        params.put(DBUtils.price, item.getPrice());
        params.put(DBUtils.currency, item.getCurrency());
        params.put(DBUtils.purchase_currency, item.getPurchaseCurrency());
        params.put(DBUtils.purchase_price, item.getPurchasePrice());
        params.put(DBUtils.inv_count, item.getInvCount());
        params.put(DBUtils.upc, item.getUpc());
        params.put(DBUtils.sku, item.getSku());
        params.put(DBUtils.active, item.isActive());
        params.put(DBUtils.changed_at, item.getChangedAt());
        params.put(DBUtils.user_id, item.getUserId());
        parameterJdbcTemplate.update(updateSql, params);
    }

    private static final String getByLocaleSql = "select i.id, i.category_id, tr.name "
            + "from item i "
            + "left join item_tr tr on tr.item_id = i.id and tr.locale = :locale "
            + "where i.id = :id";

    @Override
    public Item get(long id, String locale) {
        return get(id, locale, getByLocaleSql, itemByLocaleMapper);
    }

    private static final String getTranslationsSql = "select * from item_tr "
            + "where item_id = :item_id";

    @Override
    public List<DescriptiveTranslation> getTranslations(long itemId) {
        try {
            return parameterJdbcTemplate.query(getTranslationsSql,
                    Collections.singletonMap(DBUtils.item_id, itemId), translationMapper);
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    private static final String deleteTrSql = "delete from item_tr where item_id = :item_id";
    private static final String createTrSql = "insert into item_tr "
            + "(item_id, name, description, locale) "
            + "values(?, ?, ?, ?)";

    public void saveTranslations(final long itemId, List<? extends Translation> translations) {
        parameterJdbcTemplate.update(deleteTrSql, Collections.singletonMap(DBUtils.item_id, itemId));

        getJdbcTemplate().batchUpdate(createTrSql,
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i)
                            throws SQLException {

                        DescriptiveTranslation translation = (DescriptiveTranslation) translations.get(i);
                        ps.setLong(1, itemId);
                        ps.setString(2, translation.getName());
                        ps.setString(3, translation.getDescription());
                        ps.setString(4, translation.getLocale());
                    }

                    public int getBatchSize() {
                        return translations.size();
                    }
                });
    }

    private static final String getUserItemSql = "select * from item i "
            + "where i.id = :id and i.user_id = :user_id";

    @Override
    public Item getUserItem(long userId, long itemId) {
        Map<String, Object> params = new HashMap<>(2);
        params.put(DBUtils.id, itemId);
        params.put(DBUtils.user_id, userId);

        try {
            return parameterJdbcTemplate.queryForObject(getUserItemSql, params, itemMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private static final String getViewDTOSql = "select i.*, itr.name, itr.description, "
            + "ctr.name as category "
            + "from item i "
            + "left join item_tr itr on itr.item_id = i.id and itr.locale = :locale "
            + "left join category_tr ctr on ctr.category_id = i.category_id and ctr.locale = :locale "
            + "where i.id = :id";

    @Override
    public ItemViewDTO getViewDTO(long id, String locale) {
        Map<String, Object> params = new HashMap<>(2);
        params.put(DBUtils.id, id);
        params.put(DBUtils.locale, locale);

        try {
            return parameterJdbcTemplate.queryForObject(getViewDTOSql, params, itemViewDTOMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private static final String getItemsCountSql = "select count(id) from item";
    @Override
    public int getItemsCount() {
        return getJdbcTemplate().queryForObject(getItemsCountSql, Integer.class);
    }

    private static final String categoryItemsCountSql = "select tr.name, count(i.id) as item_count " +
            "from category c " +
            "inner join category_tr tr on tr.category_id = c.id and tr.locale = 'en_US' " +
            "left join item i on i.id = c.id " +
            "group by tr.name,i.id " +
            "order by tr.name;";
    @Override
    public List<Pair<String, Integer>> categoryItemsCount(String locale) {
        try {
            return parameterJdbcTemplate.query(categoryItemsCountSql,
                    Collections.singletonMap(DBUtils.locale, locale), categoryItemsCountExtractor);
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    private static final String searchSql = "select i.id, i.price, i.currency, i.upc, i.active, "
            + "i.created_at, tr.name, 'thumbnail.png' as thumbnail, cat_tr.name as category "
            + "from item i "
            + "left join item_tr tr on tr.item_id = i.id and tr.locale = :locale "
            + "left join category_tr cat_tr on cat_tr.category_id = i.category_id and cat_tr.locale = :locale "
            + "where 1=1 ";
    private static final String limitSql = " limit :offset, :rowsPerPage";
    private static final String countSql = "select count(i.id) from item i where 1=1 ";

    @Override
    public ListPaginatedResult<ItemListDTO> search(ItemSearchQuery itemSearchQuery) {
        int rowsPerPage = itemSearchQuery.getPageSize();
        int pageNum = itemSearchQuery.getPage();
        int offset = (pageNum - 1) * rowsPerPage;

        Map<String, Object> params = new HashMap<>(3);
        params.put(DBUtils.offset, offset);
        params.put(DBUtils.rows_per_page, rowsPerPage);
        params.put(DBUtils.locale, itemSearchQuery.getLocale());

        String whereClause = createWhereClause(itemSearchQuery, params);
        StringBuilder query = new StringBuilder(searchSql);
        query.append(whereClause);
        query.append(createOrderByClause(itemSearchQuery.getSortBy(), itemSearchQuery.isAsc()));
        query.append(limitSql);

        ListPaginatedResult<ItemListDTO> result = new ListPaginatedResult<>();
        List<ItemListDTO> items = parameterJdbcTemplate.query(query.toString(), params, itemListDTOExtractor);
        result.setList(items);

        int total = parameterJdbcTemplate.queryForObject(countSql + whereClause,
                params, Integer.class);
        result.setTotal(total);
        return result;
    }

    private String createWhereClause(ItemSearchQuery searchQuery, Map<String, Object> params) {
        StringBuilder sql = new StringBuilder();
        sql.append(" and i.user_id = :user_id");
        params.put(DBUtils.user_id, searchQuery.getUserId());

        // category
        long categoryId = searchQuery.getCategoryId();

        if (categoryId != 0) {
            sql.append(" and i.category_id = :category_id ");
            params.put(DBUtils.category_id, categoryId);
        }

        // name
        String name = searchQuery.getName();

        if (StringUtils.hasText(name)) {
            sql.append(" and tr.name like :name ");
            params.put(DBUtils.name, "%" + name + "%");
        }

        // upc
        String upc = searchQuery.getUpc();

        if (StringUtils.hasText(upc)) {
            sql.append(" and i.upc = :upc ");
            params.put(DBUtils.upc, upc);
        }

        // status
        int status = searchQuery.getStatus();

        if (status != -1) {
            sql.append(" and i.active = :status ");
            params.put(DBUtils.status, status);
        }

        return sql.toString();
    }

    private String createOrderByClause(String sortBy, boolean ascending) {
        String result = "";

        if (TextUtils.hasText(sortBy)) {
            String sortByCol = sortByColumnMap.get(sortBy);

            if (TextUtils.hasText(sortByCol)) {
                result += " order by " + sortByCol;

                if (ascending) {
                    result = result.replaceAll("AAA", "");
                } else {
                    result = result.replaceAll("AAA", "desc");
                }
            }
        }

        return result;
    }
}
