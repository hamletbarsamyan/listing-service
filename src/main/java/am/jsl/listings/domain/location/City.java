package am.jsl.listings.domain.location;


import am.jsl.listings.domain.Descriptive;

import java.io.Serializable;

/**
 * The city domain object.
 *
 * @author hamlet
 */
public class City extends Descriptive implements Serializable {

	/**
	 * The state id of this city
	 */
	private long stateId;

	/**
	 * Gets state id.
	 *
	 * @return the state id
	 */
	public long getStateId() {
		return stateId;
	}

	/**
	 * Sets state id.
	 *
	 * @param stateId the state id
	 */
	public void setStateId(long stateId) {
		this.stateId = stateId;
	}

}
