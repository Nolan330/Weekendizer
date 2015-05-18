package example.web.model;

public class Segment {
	
	public Airport DepartureAirport;
	public Airport ArrivalAirport;
	public Airline MarketingAirline;
	public Integer ElapsedTime;
	public String DepartureDateTime;
	public String ArrivalDateTime;
	public Integer FlightNumber;
	public Airline OperatingAirline;
	
	public String toString() {
		return DepartureAirport.toString()
				+ " on " + DepartureDateTime + " to "
				+ ArrivalAirport.toString()
				+ " on " + ArrivalDateTime
				+ " via flight #" + FlightNumber
				+ " with " + OperatingAirline.toString();
	}

}
