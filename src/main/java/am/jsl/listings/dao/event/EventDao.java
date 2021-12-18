package am.jsl.listings.dao.event;

import am.jsl.listings.domain.event.Event;
import am.jsl.listings.dto.event.EventListDTO;
import am.jsl.listings.search.EventSearchQuery;
import am.jsl.listings.search.ListPaginatedResult;

/**
 * The Dao interface for accessing {@link Event} domain object.
 * @author hamlet
 */
public interface EventDao {
	/**
	 * Saves the given event.
	 * @param event the event
	 */
	void saveEvent(Event event);

	/**
	 * Retrieves paginated result for the given search query.
	 * @param searchQuery the {@link EventSearchQuery} containing query options
	 * @return the {@link ListPaginatedResult} result
	 */
	ListPaginatedResult<EventListDTO> search(EventSearchQuery searchQuery);
}
