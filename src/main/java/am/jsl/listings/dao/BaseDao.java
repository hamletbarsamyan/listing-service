package am.jsl.listings.dao;

import java.util.List;

/**
 * Base DAO interface for interacting with the domain Entity objects with generic way.
 *
 * @param <T> the parametrisation entity type.
 * @author hamlet
 */
public interface BaseDao<T> {
    /**
     * Returns all instances of the type.
     *
     * @return the results
     */
    List<T> list();

    /**
     * Returns whether an entity with the given id can be deleted.
     *
     * @param id the entity id
     * @return true if an entity with the given id can be deleted
     */
    boolean canDelete(long id);

    /**
     * Deletes the entity with the given id.
     *
     * @param id the entity id
     */
    void delete(long id);

    /**
     * Returns whether an entity with the given id.
     *
     * @param name the entity name
     * @param id   the entity id
     * @return true if an entity with the given id
     */
    boolean exists(String name, long id);

    /**
     * Creates the given entity.
     *
     * @param object the entity
     */
    void create(T object);

    /**
     * Updates the given entity.
     *
     * @param object the entity
     */
    void update(T object);

    /**
     * Retrieves an entity by id .
     *
     * @param id the entity id
     * @return the entity
     */
    T get(long id);


    /**
     * Returns all instances of the type.
     * Will be selected instance id and name.
     *
     * @return the result
     */
    List<T> lookup();
}
