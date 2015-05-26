package example.web.responses;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import example.web.model.City;

public class CityInfoResponse {

	@SerializedName("Cities")
	public List<City> mCities;
	
}
