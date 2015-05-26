package example.web.requests;

import com.google.gson.annotations.SerializedName;

import example.web.model.City;

public class WeekendPlannerRequest {
	
	@SerializedName("budget")
	private String mBudget;
	
	@SerializedName("currentCity")
	private City mOriginCity;
	
	@SerializedName("destinationCity")
	private City mDestinationCity;
	
	public Double getBudget() {
		return Double.valueOf(mBudget);
	}
	
	public City getOriginCity() {
		return mOriginCity;
	}
	
	public City getDestinationCity() {
		return mDestinationCity;
	}
	
	public String toString() {
		return "{budget: " + mBudget 
				+ ", curCity: "
				+ mOriginCity.getName() + " (" + mOriginCity.getCode() + ")"
				+ ", destCity: "
				+ (mDestinationCity == null ? 
					"null" :
					mDestinationCity.getName() + " (" + mDestinationCity.getCode() + ")")
				+ "}";
	}

}
