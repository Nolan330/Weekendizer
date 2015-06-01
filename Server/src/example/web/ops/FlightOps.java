package example.web.ops;

import example.web.responses.CityResponse;
import example.web.responses.FlightResponse;
import example.web.responses.OAuth2TokenResponse;
import example.web.services.FlightService;
import example.web.utils.BaseOAuth2Utils;
import example.web.utils.FlightAuthUtils;

public class FlightOps extends BaseOps<FlightService> {
	
	/**
	 * Default flight parameters
	 */
	private final String FLIGHT_DEPARTURE_WINDOW = "17002359";
	private final String FLIGHT_RETURN_WINDOW = "12002359";
	private final String LIMIT_RESPONSES = "1";
	
	public FlightOps(String endpoint) {
		super(endpoint, FlightService.class, new FlightAuthUtils());
	}

	@Override
	protected OAuth2TokenResponse authorize() { 
		System.out.println("FlightOps::authorize - " + System.currentTimeMillis());
		return mService.authorize(
			mAuthUtils.makeCredential(BaseOAuth2Utils.USER_TOKEN),
			mAuthUtils.getGrantType());
	}
	
	public CityResponse getCities(String authToken, String country) {
		System.out.println("FlightOps::getCities - " + System.currentTimeMillis());
		return mService.queryCities(
			authToken,
			country);
	}
	
	public FlightResponse getFlight(
			String authToken, String origin, String destination,
			String departureDate, String returnDate, String maxFare) {
		// If the origin and destination city are the same,
		// return a mock FlightInfoResponse with a fare of $0.00
		System.out.println("FlightOps::getFlight - " + System.currentTimeMillis());
		return origin.equals(destination) ?
			new FlightResponse(
				origin, destination, departureDate, returnDate) :
			mService.queryFlights(
				authToken,
				origin,
				destination,
				departureDate,
				returnDate,
				maxFare,
				FLIGHT_DEPARTURE_WINDOW,
				FLIGHT_RETURN_WINDOW,
				LIMIT_RESPONSES);
	}

}
