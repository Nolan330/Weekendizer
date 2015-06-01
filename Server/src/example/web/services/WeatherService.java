package example.web.services;

import example.web.responses.WeatherResponse;
import retrofit.http.GET;
import retrofit.http.Query;

public interface WeatherService {

	@GET("/data/2.5/forecast/daily")
	WeatherResponse queryWeather(
		@Query("q") String city,
		@Query("cnt") Integer dayCount,
		@Query("units") String units,
		@Query("mode") String responseMode);
	
}
