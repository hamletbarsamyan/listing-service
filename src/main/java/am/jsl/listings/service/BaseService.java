package am.jsl.listings.service;

import am.jsl.listings.ex.CannotDeleteException;

import java.util.List;

/**
 * Service interface which defines all the methods for working with the domain objects with generic way.
 *
 * @param <T> the parametrisation entity type.
 * @author hamlet
 */
public interface BaseService<T> {
    /**
     * Returns all instances of the type.
     *
     * @return the results
     */
    List<T> list();

    /**
     * Deletes the entity with the given id.
     * Will throw {@link CannotDeleteException} if entity can not be deleted.
     *
     * @param id the entity id
     * @throws CannotDeleteException if entity can not be deleted
     */
    void delete(long id) throws CannotDeleteException;

    /**
     * Returns whether an entity with the given id.
     *
     * @param name the entity name
     * @param id   the entity id
     * @return true if an entity with the given id exists
     */
    boolean exists(String name, long id);

    /**
     * Creates the given entity.
     *
     * @param object the entity
     */
    void create(T object) throws Exception;

    /**
     * Updates the given entity.
     *
     * @param object the entity
     */
    void update(T object) throws Exception;

    /**
     * Retrieves an entity by its id.
     *
     * @param id the entity id
     * @return the entity
     */
    T get(long id);

    /**
     * Returns all instances of the type.
     * Will be queried instance id and name.
     *
     * @return the result
     */
    List<T> lookup();
}
