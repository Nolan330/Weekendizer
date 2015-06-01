package example.web.responses;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import example.web.model.Weather;

public class WeatherResponse {
	
	private static final Integer WEEKEND_LENGTH = 3;

	@SerializedName("list")
	private List<Weather> mWeather;
	
	public List<Weather> getWeekendWeather() {
		return mWeather.size() > WEEKEND_LENGTH ?
			mWeather.subList(
				mWeather.size() - WEEKEND_LENGTH, mWeather.size()) :
			mWeather;
	}
	
	public String toString() {
		return "Weekend Forecast: " + getWeekendWeather();
	}
	
}
