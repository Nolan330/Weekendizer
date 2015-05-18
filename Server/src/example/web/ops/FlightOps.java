package example.web.ops;

import example.web.responses.CityInfoResponse;
import example.web.responses.FlightInfoResponse;
import example.web.services.FlightInfoService;
import example.web.utils.FlightAuthUtils;

public class FlightOps extends BaseOps<FlightInfoService> {
	
	/**
	 * Default flight parameters
	 */
	private final String FLIGHT_DEPARTURE_WINDOW = "17002359";
	private final String FLIGHT_RETURN_WINDOW = "12002359";
	private final String LIMIT_RESPONSES = "1";
	
	public FlightOps(String endpoint) {
		super(endpoint, FlightInfoService.class, new FlightAuthUtils());
	}

	@Override
	public String authorize() { 
		return mAuthUtils.makeBearerToken(
			mService.authorize(
				mAuthUtils.makeCredential(),
				mAuthUtils.getGrantType()).access_token);
	}
	
	public CityInfoResponse getCities(String authToken, String country) {
		return mService.queryCities(authToken, country);
	}
	
	public FlightInfoResponse getFlight(
			String authToken, String origin, String destination,
			String departureDate, String returnDate, String maxFare) {
		// check if dest == origin
		return mService.queryFlights(
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
