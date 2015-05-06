package example.web.requests;

public class WeekendPlannerRequest {
	
	public String budget;
	public CityCode currentCity;
	public CityCode destinationCity;
	
	public WeekendPlannerRequest(String b,
			CityCode curCity, CityCode destCity) {
		budget = b;
		currentCity = curCity;
		destinationCity = destCity;
	}
	
	public String toString() {
		return "{budget: " + budget 
				+ ", curCity: " + currentCity.code
				+ ", destCity: " + destinationCity.code + "}";
	}

}
