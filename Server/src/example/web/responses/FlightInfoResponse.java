package example.web.responses;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import com.google.gson.annotations.SerializedName;

import example.web.model.Flight;

public class FlightInfoResponse {

	@SerializedName("PricedItineraries")
	private List<Flight> mPricedItineraries;
	
	@SerializedName("ReturnDateTime")
	private String mReturnDateTime;
	
	@SerializedName("DepartureDateTime")
	private String mDepartureDateTime;
	
	@SerializedName("DestinationLocation")
	private String mDestinationLocation;
	
	@SerializedName("OriginLocation")
	private String mOriginLocation;
	
	public FlightInfoResponse(String returnDateTime,
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
