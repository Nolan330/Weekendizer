package example.web.requests;

import example.web.model.City;

public class WeekendPlannerRequest {
	
	public String budget;
	public City currentCity;
	public City destinationCity;
	
	public WeekendPlannerRequest(String b, City curCity, City destCity) {
		budget = b;
		currentCity = curCity;
		destinationCity = destCity;
	}
	
	public String toString() {
		return "{budget: " + budget 
				+ ", curCity: "
				+ currentCity.name + " (" + currentCity.code + ")"
				+ ", destCity: "
				+ (destinationCity == null ? 
					"null" :
					destinationCity.name + " (" + destinationCity.code + ")")
				+ "}";
	}

}
