package am.jsl.listings.dao;

import am.jsl.listings.domain.NamedEntity;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of BaseDao interface with Springframework's JDBC Dao support.
 * <p>Requires a {@link DataSource} to be set.
 *
 * @param <T> the type parameter
 * @author hamlet
 */
public class BaseDaoImpl<T> extends AbstractDaoImpl implements BaseDao<T> {

    /**
     * The row mapper for generic {@link NamedEntity} type.
     */
    protected NamedEntityMapper namedMapper = new NamedEntityMapper();

    /**
     * Creates a new BaseDaoImpl for the given {@link DataSource}.
     *
     * @param dataSource the JDBC DataSource to access
     */
    public BaseDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public List<T> list() {
        return null;
    }
    @Override
    public boolean canDelete(long id) {
        return true;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public boolean exists(String name, long id) {
        return false;
    }

    @Override
    public void create(T object) {

    }

    @Override
    public void update(T object) {

    }

    @Override
    public T get(long id) {
        return null;
    }

    @Override
    public List<T> lookup() {
        return null;
    }

    /**
     * Retrieves an entity by id and sql.
     * @param id     the entity id
     * @param sql    the sql for querying the entity
     * @param mapper the mapper for mapping entity from a {@link java.sql.ResultSet}
     * @return the entity
     */
    protected T get(long id, String sql, RowMapper<T> mapper) {
        try {
            return parameterJdbcTemplate.queryForObject(sql,
                    Collections.singletonMap(DBUtils.id, id), mapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * Returns all instances of the NamedEntity.
     * @param sql    the sql for querying entities
     * @return the list
     */
    protected List<NamedEntity> lookupBySql(String sql) {
        return parameterJdbcTemplate.query(sql, namedMapper);
    }

    /**
     * Returns all instances of the type.
     * @param sql       the sql for querying entities
     * @param rowMapper the mapper for mapping entities from a {@link java.sql.ResultSet}
     * @return the list
     */
    protected List<T> lookup(String sql, RowMapper<T> rowMapper) {
        return parameterJdbcTemplate.query(sql, rowMapper);
    }

    /**
     * Deletes the entity with the given id.
     * @param id     the entity id
     * @param sql    the sql for deleting the entity
     */
    protected void delete(long id, String sql) {
        parameterJdbcTemplate.update(sql, Collections.singletonMap(DBUtils.id, id));
    }

    /**
     * Returns whether string exists in entity with the given id exists.
     * @param name   the entity name
     * @param id     the entity id
     * @param sql    the sql for querying the entity
     * @return the boolean
     */
    protected boolean exists(String name, long id, String sql) {
        Map<String, Object> params = new HashMap<>(2);
        params.put(DBUtils.name, name);
        params.put(DBUtils.id, id);
        List<Long> list = parameterJdbcTemplate.queryForList(sql, params, Long.class);

        return !list.isEmpty();
    }

    /**
     * Returns whether an entity with the given id can be deleted.
     *
     * @param id     the entity id
     * @param query  the sql for querying the entity
     * @return the boolean
     */
    protected boolean canDelete(long id, String query) {
        List<Long> list = parameterJdbcTemplate.queryForList(query,
                Collections.singletonMap(DBUtils.id, id), Long.class);

        return list.isEmpty();
    }
}
