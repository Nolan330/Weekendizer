package example.web.ops;

import example.web.services.WeatherInfoService;

public class WeatherOps extends BaseOps<WeatherInfoService> {
	
	public WeatherOps(String endpoint) {
		super(endpoint, WeatherInfoService.class);
	}

	@Override
	public String authorize() {
		// no-op for non-authorized APIs
		return null;
	}

}
