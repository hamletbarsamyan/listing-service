package am.jsl.listings.domain.location;


import am.jsl.listings.domain.Descriptive;

import java.io.Serializable;

/**
 * The state domain object.
 *
 * @author hamlet
 */
public class State extends Descriptive implements Serializable {

	/**
	 * The country id of this state
	 */
	private long countryId;

	/**
	 * Gets country id.
	 *
	 * @return the country id
	 */
	public long getCountryId() {
		return countryId;
	}

	/**
	 * Sets country id.
	 *
	 * @param countryId the country id
	 */
	public void setCountryId(long countryId) {
		this.countryId = countryId;
	}

}
