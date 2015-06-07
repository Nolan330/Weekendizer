package example.web.responses;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import example.web.model.City;
import example.web.model.Event;
import example.web.model.Flight;
import example.web.model.TripVariant;
import example.web.model.Weather;

/**
 * WeekendPlannerResponse is responsible for holding the
 * response weekend data sent back to the client. The
 * asynchronous operations directly update this response
 * so that additional state beyond what is needed for the
 * weekend response is not held. It is also to centralize
 * computations relating to planning the weekend, such as
 * selecting the various events for each day, tracking
 * remaining budget for each variant of the weekend, etc.
 */
public class WeekendPlannerResponse {
	
	/**
	 * A list of different variations of things to do
	 * at the destination city this weekend, each keeping
	 * track of the individual remaining budget
	 */
	@SerializedName("tripVariants")
	private List<TripVariant> mTripVariants;
	
	/**
	 * The initial budget provided by the client
	 */
	@SerializedName("initBudget")
	private Double mInitBudget;
	
	/**
	 * The origin city provided by the client
	 */
	@SerializedName("originCity")
	private City mOriginCity;
	
	/**
	 * The destination city provided by the client
	 */
	@SerializedName("destinationCity")
	private City mDestinationCity;
	
	/**
	 * The flight used for the trip, returned by
	 * WeekendPlannerOps::getFlight which queries the Sabre Flights API
	 */
	@SerializedName("flight")
	private Flight mFlight;
	
	/**
	 * The weekend weather forecast at the destination city
	 */
	@SerializedName("weather")
	private List<Weather> mWeather;
	
	public Double getInitialBudget() {
		return mInitBudget;
	}
	
	public String getOriginCityCode() {
		return mOriginCity.getCode();
	}
	
	public String getDestinationCityCode() {
		return mDestinationCity.getCode();
	}
	
	public String getDestinationCityName() {
		return mDestinationCity.getName();
	}
	
	public Double getBudgetAfterFlight() {
		return mInitBudget - mFlight.getFare();
	}
	
	public Flight getFlight() {
		return mFlight;
	}
	
	public String getDepartingArrivalDateTime() {
		return mFlight.getDepartingArrivalDateTime();
	}
	
	public String getReturningDepartureDateTime() {
		return mFlight.getReturningDepartureDateTime();
	}
	
	public List<Weather> getWeather() {
		return mWeather;
	}
	
	public String toString() {
		String variants = "";
		for(TripVariant tv : mTripVariants) {
			String events = "";
			for(Event e : tv.getSchedule()) {
				events += "\n\t\t" + e;
			}
			variants += "\n\tVariant:" + events;
		}
		return "Flight: " + mFlight + "\nVariants:" + variants;
	}

}
