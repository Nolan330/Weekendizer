package example.web.responses;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import example.web.model.Flight;

public class FlightInfoResponse {

	private List<Flight> PricedItineraries;
	private String ReturnDateTime;
	private String DepartureDateTime;
	private String DestinationLocation;
	private String OriginLocation;
	
	public FlightInfoResponse(String returnDateTime,
			String departureDateTime, String destinationLocation,
			String originLocation) {
		PricedItineraries = Arrays.asList(new Flight());
		ReturnDateTime = returnDateTime;
		DepartureDateTime = departureDateTime;
		DestinationLocation = destinationLocation;
		OriginLocation = originLocation;
	}
	
	public Double getFare() {
		return getFlight().getFare();
	}
	
	public LocalDate getDepartureDate() {
		return LocalDate.parse(DepartureDateTime);
	}
	
	public LocalDateTime getDepartureDateTime() {
		return LocalDateTime.parse(PricedItineraries.get(0).getDeparutureDateTime());
	}

	public LocalDate getReturnDate() {
		return LocalDate.parse(PricedItineraries.get(0).getReturnDateTime());
	}
	
	public LocalDateTime getReturnDateTime() {
		return LocalDateTime.parse(ReturnDateTime);
	}

	public Flight getFlight() {
		return PricedItineraries.get(0);
	}

	public String toString() {
		String flightItin = "";
		for(Flight f : PricedItineraries) {
			flightItin += "\n" + f.toString();
		}
		return "Depart from " + OriginLocation
				+ " on " + DepartureDateTime
				+ "\nto " + DestinationLocation
				+ ", returing on " + ReturnDateTime 
				+ " by " + flightItin;
	}
	
}
