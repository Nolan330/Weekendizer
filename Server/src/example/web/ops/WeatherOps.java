package example.web.ops;

import example.web.responses.OAuth2TokenResponse;
import example.web.responses.WeatherResponse;
import example.web.services.WeatherService;

public class WeatherOps extends BaseOps<WeatherService> {
	
	/**
	 * Default values
	 */
	private final String UNITS = "imperial";
	private final String RESPONSE_MODE = "json";
	
	public WeatherOps(String endpoint) {
		super(endpoint, WeatherService.class);
	}

	@Override
	protected OAuth2TokenResponse authorize() {
		System.out.println("WeatherOps::authorize - " + System.currentTimeMillis());
		// no-op for non-authorized APIs
		return null;
	}
	
	public WeatherResponse getWeather(String city, Integer dayCount) {
		System.out.println("WeatherOps::getWeather - " + System.currentTimeMillis());
		return mService.queryWeather(
			city,
			dayCount,
			UNITS,
			RESPONSE_MODE);
	}

}
