package example.web.ops;

import java.util.Arrays;
import java.util.List;

import example.web.model.Weather;
import example.web.responses.GeoCodeResponse;
import example.web.responses.OAuth2TokenResponse;
import example.web.responses.PlacesResponse;
import example.web.services.PlacesService;
import example.web.utils.BaseOAuth2Utils;
import example.web.utils.PlacesAuthUtils;

/**
 * Provides API-specific constants and an interface to interact
 * with the Google Places API
 */
public class PlacesOps extends BaseOps<PlacesService> {
	
	/**
	 * Default values
	 */
	private final List<String> OUTDOOR_CONDITIONS = 
		Arrays.asList("Additional", "Clouds");
	private final String INDOOR_PLACES =
		"aquarium|art_gallery|bowling_alley|movie_theater|museum|shopping_mall";
	private final String OUTDOOR_PLACES =
		"amusement_park|campground|park|stadium|zoo";
	private final Integer RADIUS_METERS = 25000;
	
	public PlacesOps(String endpoint) {
		super(endpoint, PlacesService.class, new PlacesAuthUtils());
	}
	
	/**
	 * Override the getAuthToken method to only return
	 * the raw application key, as it is a query parameter rather
	 * than a request header, and does not need the "Bearer" 
	 * specification
	 */
	@Override
	public String getAuthToken() {
		return authorize().getAccessToken();
	}

	/**
	 * Provides the appropriate Application token for the API
	 */
	@Override
	protected OAuth2TokenResponse authorize() {
		System.out.println("PlacesOps::authorize - " + System.currentTimeMillis());
		return new OAuth2TokenResponse(
			mAuthUtils.makeCredential(BaseOAuth2Utils.APP_TOKEN));
	}
	
	/**
	 * Queries the API for places within 25000 meters of the given
	 * lat, lng coordinates that are appropriate for the given weather
	 * conditions
	 */
	public PlacesResponse getPlaces(String authToken,
			String lat, String lng, Weather weather) {
		System.out.println("PlacesOps::getPlaces - " + System.currentTimeMillis());
		return mService.queryPlaces(
			authToken,
			lat + "," + lng,
			RADIUS_METERS,
			OUTDOOR_CONDITIONS.contains(weather.getDayCondition()) ?
				OUTDOOR_PLACES : INDOOR_PLACES);
	}
	
	/**
	 * Queries the API for the lat, lng coodrinates of a given city
	 */
	public GeoCodeResponse getGeocode(String authToken, String city) {
		System.out.println("PlacesOps::getGeocode - " + System.currentTimeMillis());
		return mService.queryGeocode(
			authToken,
			city);
	}

}
