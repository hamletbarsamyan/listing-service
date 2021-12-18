package am.jsl.listings.dao;

import am.jsl.listings.domain.Descriptive;
import am.jsl.listings.domain.NamedEntity;
import am.jsl.listings.domain.Translation;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of MultiLingualDao interface with Springframework's JDBC Dao support.
 * <p>Requires a {@link DataSource} to be set.
 *
 * @param <T> the type parameter
 * @author hamlet
 */
public class MultiLingualDaoImpl<T> extends BaseDaoImpl<T> implements MultiLingualDao<T> {

    /**
     * Creates a new MultiLingualDaoImpl for the given {@link DataSource}.
     *
     * @param dataSource the JDBC DataSource to access
     */
    public MultiLingualDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public List<T> list(String locale) {
        return null;
    }

    /**
     * Returns the list for the given locale.
     *
     * @param locale    the locale
     * @param sql       the sql
     * @param rowMapper the row mapper
     * @return the list
     */
    protected List<T> list(String locale, String sql, RowMapper<T> rowMapper) {
        return parameterJdbcTemplate.query(sql,
                Collections.singletonMap(DBUtils.locale, locale), rowMapper);
    }


    @Override
    public T get(long id, String locale) {
        return null;
    }

    @Override
    public List<T> lookup(String locale) {
        return null;
    }

    @Override
    public List<T> lookup(long parentId, String locale) {
        return null;
    }

    @Override
    public List<? extends Translation> getTranslations(long entityId) {
        return null;
    }

    @Override
    public void saveTranslations(long entityId, List<? extends Translation> translations) {

    }

    /**
     * Retrieves an entity by id, locale and sql.
     * @param id     the entity id
     * @param locale the locale
     * @param sql    the sql for querying the entity
     * @param mapper the mapper for mapping entity from a {@link java.sql.ResultSet}
     * @return the entity
     */
    protected T get(long id, String locale, String sql, RowMapper<T> mapper) {
        Map<String, Object> params = new HashMap<>(2);
        params.put(DBUtils.id, id);
        params.put(DBUtils.locale, locale);

        try {
            return parameterJdbcTemplate.queryForObject(sql, params, mapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * Returns all instances of the NamedEntity for the given locale.
     * @param locale the locale associated with the entity
     * @param sql    the sql for querying entities
     * @return the list
     */
    protected List<NamedEntity> lookup(String locale, String sql) {
        return parameterJdbcTemplate.query(sql,
                Collections.singletonMap(DBUtils.locale, locale), namedMapper);
    }

    /**
     * Returns all instances of the type for the given locale.
     * @param locale    the locale associated with the entity
     * @param sql       the sql for querying entities
     * @param rowMapper the mapper for mapping entities from a {@link java.sql.ResultSet}
     * @return the list
     */
    protected List<T> lookup(String locale, String sql, RowMapper<T> rowMapper) {
        return parameterJdbcTemplate.query(sql,
                Collections.singletonMap(DBUtils.locale, locale), rowMapper);
    }

    /**
     * Saves the translation fields of the given descriptive.
     * @param descriptive the Descriptive
     * @param descriptiveKey the description key
     * @param sql the sql to execute
     * @return the updated row count
     */
    protected int saveTranslation(Descriptive descriptive, String descriptiveKey,  String sql) {
        Map<String, Object> params = new HashMap<>(4);
        params.put(descriptiveKey, descriptive.getId());
        params.put(DBUtils.name, descriptive.getName());
        params.put(DBUtils.description, descriptive.getDescription());
        params.put(DBUtils.locale, descriptive.getLocale());
        return parameterJdbcTemplate.update(sql, params);
    }
}
