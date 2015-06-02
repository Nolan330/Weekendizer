package example.web.responses;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import com.google.gson.annotations.SerializedName;

import example.web.model.Flight;

/**
 * The top-level POJO returned by the Sabre Flights API
 */
public class FlightResponse {

	/**
	 * The itinerary for the flights; the request is limited to one flight
	 * so this list will only ever be of size 1
	 */
	@SerializedName("PricedItineraries")
	private List<Flight> mPricedItineraries;
	
	/**
	 * The return date time of the flight
	 */
	@SerializedName("ReturnDateTime")
	private String mReturnDateTime;
	
	/**
	 * The departure date time of the flight
	 */
	@SerializedName("DepartureDateTime")
	private String mDepartureDateTime;
	
	/**
	 * The destination location
	 */
	@SerializedName("DestinationLocation")
	private String mDestinationLocation;
	
	/**
	 * The origin location
	 */
	@SerializedName("OriginLocation")
	private String mOriginLocation;
	
	/**
	 * This constructor exists for when the origin and destination
	 * cities are the same city, and the Flight response is to logically ignored
	 * (cost $0USD, and give default date times for arrival and departure)
	 */
	public FlightResponse(String returnDateTime,
			String departureDateTime, String destinationLocation,
			String originLocation) {
		mPricedItineraries = Arrays.asList(new Flight());
		mReturnDateTime = returnDateTime;
		mDepartureDateTime = departureDateTime;
		mDestinationLocation = destinationLocation;
		mOriginLocation = originLocation;
	}
	
	public Double getFare() {
		return getFlight().getFare();
	}
	
	public LocalDate getDepartureDate() {
		return LocalDate.parse(mDepartureDateTime);
	}
	
	public LocalDateTime getDepartingDepartureDateTime() {
		return LocalDateTime.parse(
			mPricedItineraries.get(0).getDepartingDepartureDateTime());
	}
	
	public LocalDateTime getDepartingArrivalDateTime() {
		return LocalDateTime.parse(
			mPricedItineraries.get(0).getDepartingArrivalDateTime());
	}

	public LocalDate getReturnDate() {
		return LocalDate.parse(mReturnDateTime);
	}
	
	public LocalDateTime getReturningDepartureDateTime() {
		return LocalDateTime.parse(
			mPricedItineraries.get(0).getReturningDepartureDateTime());
	}
	
	public LocalDateTime getReturningArrivalDateTime() {
		return LocalDateTime.parse(
			mPricedItineraries.get(0).getReturningArrivalDateTime());
	}

	public Flight getFlight() {
		return mPricedItineraries.get(0);
	}

	public String toString() {
		String flightItin = "";
		for(Flight f : mPricedItineraries) {
			flightItin += "\n" + f.toString();
		}
		return "Depart from " + mOriginLocation
				+ " on " + mDepartureDateTime
				+ "\nto " + mDestinationLocation
				+ ", returing on " + mReturnDateTime 
				+ " by " + flightItin;
	}
	
}
