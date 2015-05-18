package example.web.responses;

import java.util.List;

import example.web.model.Flight;
import example.web.model.Link;

public class FlightInfoResponse {

	public List<Flight> PricedItineraries;
	public String ReturnDateTime;
	public String DepartureDateTime;
	public String DestinationLocation;
	public String OriginLocation;
	public List<Link> Links;
 	
	public FlightInfoResponse(List<Flight> pricedItineraries,
			String returnDateTime, String departureDateTime,
			String destinationLocation, String originLocation,
			List<Link> links) {
		PricedItineraries = pricedItineraries;
		ReturnDateTime = returnDateTime;
		DepartureDateTime = departureDateTime;
		DestinationLocation = destinationLocation;
		OriginLocation = originLocation;
		Links = links;
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
