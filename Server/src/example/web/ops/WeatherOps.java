package example.web.ops;

import example.web.responses.OAuth2TokenResponse;
import example.web.responses.WeatherResponse;
import example.web.services.WeatherService;

/**
 * Provides API-specific constants and an interface to interact
 * with the OpenWeatherMaps API
 */
public class WeatherOps extends BaseOps<WeatherService> {
	
	/**
	 * Default values
	 */
	private final String UNITS = "imperial";
	private final String RESPONSE_MODE = "json";
	
	public WeatherOps(String endpoint) {
		super(endpoint, WeatherService.class);
	}

	/**
	 * Override the authorize () abstract method, which in this case
	 * is a no-op, because the OpenWeatherMap forecast is an unauthorized API call
	 */
	@Override
	protected OAuth2TokenResponse authorize() {
		System.out.println("WeatherOps::authorize - " + System.currentTimeMillis());
		// no-op for non-authorized APIs
		return null;
	}
	
	/**
	 * Invoke the WeatherService retrofit adapter to query the endpoint
	 * for the weather forecast
	 */
	public WeatherResponse getWeather(String city, Integer dayCount) {
		System.out.println("WeatherOps::getWeather - " + System.currentTimeMillis());
		return mService.queryWeather(
			city,
			dayCount,
			UNITS,
			RESPONSE_MODE);
	}

}
