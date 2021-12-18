package am.jsl.listings.domain.location;


import am.jsl.listings.domain.Descriptive;

import java.io.Serializable;

/**
 * The district domain object.
 *
 * @author hamlet
 */
public class District extends Descriptive implements Serializable {

    /**
     * The city id of this district
	 */
	private long cityId;

	/**
	 * Gets city id.
	 *
	 * @return the city id
	 */
	public long getCityId() {
		return cityId;
	}

	/**
	 * Sets city id.
	 *
	 * @param cityId the city id
	 */
	public void setCityId(long cityId) {
		this.cityId = cityId;
	}
}
