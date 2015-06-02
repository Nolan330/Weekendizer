package example.weekendplanner;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import example.web.model.City;
import example.web.model.Event;
import example.web.model.Flight;
import example.web.model.TripVariant;
import example.web.model.Weather;
import example.web.responses.FlightResponse;
import example.web.responses.PlacesResponse;
import example.web.responses.TicketResponse;
import example.web.responses.WeatherResponse;

public class WeekendPlannerResponse {
	
	@SerializedName("tripVariants")
	private List<TripVariant> mTripVariants;
	
	@SerializedName("initBudget")
	private final Double mInitBudget;
	
	@SerializedName("originCity")
	private final City mOriginCity;
	
	@SerializedName("destinationCity")
	private final City mDestinationCity;
	
	@SerializedName("flight")
	private Flight mFlight;
	
	@SerializedName("weather")
	private List<Weather> mWeather;
	
	@Expose(serialize = false, deserialize = false)
	private Iterator<TripVariant> mEventIt;

	public WeekendPlannerResponse(Double initBudget,
			City originCity, City destCity, int numVariants) {
		mInitBudget = initBudget;
		mOriginCity = originCity;
		mDestinationCity = destCity;
		mTripVariants = 
			Stream.generate(() -> new TripVariant(mInitBudget))
				.limit(numVariants)
				.collect(Collectors.toList());
	}
	
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
	
	public LocalDateTime getDepartingArrivalDateTime() {
		return LocalDateTime.parse(mFlight.getDepartingArrivalDateTime());
	}
	
	public LocalDateTime getReturningDepartureDateTime() {
		return LocalDateTime.parse(mFlight.getReturningDepartureDateTime());
	}
	
	public List<Weather> getWeather() {
		return mWeather;
	}
	
	public WeekendPlannerResponse update(FlightResponse response) {
		mFlight = response.getFlight();
		return this;
	}
	
	public WeekendPlannerResponse update(TicketResponse response) {
		mEventIt = mTripVariants.iterator();
		response.getEvents().stream()
			.distinct()
			.forEach(event -> {
				if (mEventIt.hasNext()) 
					mEventIt.next().addEvent(event);
				else
					mEventIt = mTripVariants.iterator();
			});
		return this;
	}
	
	public WeekendPlannerResponse update(WeatherResponse response) {
		mWeather = response.getWeekendWeather();
		return this;
	}
	
	public WeekendPlannerResponse update(PlacesResponse response) {
		mTripVariants.stream()
			.forEach(trip -> trip.addPlaces(response.getRandomPlaces()));
		return this;
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
