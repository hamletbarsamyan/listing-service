package am.jsl.listings.service.event;

import am.jsl.listings.dao.event.EventDao;
import am.jsl.listings.domain.event.Event;
import am.jsl.listings.domain.event.EventType;
import am.jsl.listings.dto.event.EventListDTO;
import am.jsl.listings.search.EventSearchQuery;
import am.jsl.listings.search.ListPaginatedResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * The service implementation of the {@link EventService}.
 * @author hamlet
 */
@Service("eventService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class EventServiceImpl implements EventService {
	
	@Autowired
	@Qualifier("eventDao")
	private EventDao eventDao;

	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void saveEvent(Event event) {
		eventDao.saveEvent(event);
	}

	@Override
	public void saveEvent(EventType eventType, String message, long performedBy) {
		Event event = new Event();
		event.setEventType(eventType.getValue());
		event.setMessage(message);
		event.setPerformedBy(performedBy);
		saveEvent(event);
	}

	@Override
	public ListPaginatedResult<EventListDTO> search(EventSearchQuery searchQuery) {
		return eventDao.search(searchQuery);
	}
}
