package am.jsl.listings.service;

import java.util.List;

/**
 * Service interface which defines all the methods for working with the Entity objects with multilingual fields
 * such as name, description which are stored in tables with _tr postfix.
 * @param <T> the parametrisation entity type.
 * @author hamlet
 */
public interface MultiLingualService<T> extends BaseService<T> {
    /**
     * Returns all instances of the type for the given locale.
     *
     * @param locale the locale
     * @return the results
     */
    List<T> list(String locale);

    /**
     * Retrieves an entity by id and locale.
     *
     * @param id         the entity id
     * @param locale the locale
     * @return the entity
     */
    T get(long id, String locale);


    /**
     * Returns all instances of the type for the given locale.
     * Will be queried instance id and name.
     *
     * @param locale the locale
     * @return the result
     */
    List<T> lookup(String locale);

    /**
     * Returns all instances of the type by parent id for the given locale.
     * Will be queried instance id and name.
     *
     * @param parentId   the parent id of entity
     * @param locale the locale
     * @return the result
     */
    List<T> lookup(long parentId, String locale);

    /**
     * Creates translation data for the given entity.
     *
     * @param object the entity
     */
    void createTranslation(T object);

    /**
     * Updates the translation data for the given entity.
     *
     * @param object the entity
     * @return the updated row count
     */
    int updateTranslation(T object);

}
