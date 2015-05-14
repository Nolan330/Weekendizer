package example.web.responses;

import java.util.List;

import example.web.model.City;

public class CityInfoResponse {

	public List<City> Cities;
	
	public CityInfoResponse(List<City> c) {
		Cities = c;
	}
	
}
