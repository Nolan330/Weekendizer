package example.web.model;

import java.util.List;

public class Flight {

	private AirItinerary AirItinerary;
	private AirItineraryPricingInfo AirItineraryPricingInfo;
	
	public Double getFare() {
		return AirItineraryPricingInfo.ItinTotalFare.TotalFare.getAmount();
	}
	
	public String getDeparutureDateTime() {
		return AirItinerary
				.OriginDestinationOptions
				.OriginDestinationOption.get(0)
				.FlightSegment.get(0)
				.DepartureDateTime;
	}
	
	public String getReturnDateTime() {
		return AirItinerary
				.OriginDestinationOptions
				.OriginDestinationOption.get(AirItinerary.OriginDestinationOptions.OriginDestinationOption.size() - 1)
				.FlightSegment.get(AirItinerary
						.OriginDestinationOptions
						.OriginDestinationOption.get(AirItinerary.OriginDestinationOptions.OriginDestinationOption.size() - 1).FlightSegment.size() - 1)
				.DepartureDateTime;
	}
	
	public String toString() {
		return this.AirItinerary.toString()
				+ "costing " + this.AirItineraryPricingInfo.toString();
	}
	
	private class AirItinerary {
		
		private OriginDestinationOptions OriginDestinationOptions;
	
		public String toString() {
			return this.OriginDestinationOptions.toString();
		}
	}
	
	private class OriginDestinationOptions {
		
		private List<FlightSegment> OriginDestinationOption;
		
		public String toString() {
			String options = "";
			for(FlightSegment f : OriginDestinationOption) {
				options += f.toString() + ",\n";
			}
			return options;
		}
	}
	
	private class FlightSegment {
		
		private List<Segment> FlightSegment;
		private Integer ElapsedTime;
		
		public String toString() {
			String segments = "";
			for(Segment s : FlightSegment) {
				segments += s.toString() + ", ";
			}
			return "\t[" + segments + "] taking " + ElapsedTime + " minutes";
		}
	}
	
	private class Segment {
		
		private Airport DepartureAirport;
		private Airport ArrivalAirport;
		private Integer ElapsedTime;
		private String DepartureDateTime;
		private String ArrivalDateTime;
		private Integer FlightNumber;
		private Airline OperatingAirline;
		
		public String toString() {
			return DepartureAirport.toString()
					+ " on " + DepartureDateTime + " to "
					+ ArrivalAirport.toString()
					+ " on " + ArrivalDateTime
					+ " (" + ElapsedTime + " minutes)"
					+ " via flight #" + FlightNumber
					+ " with " + OperatingAirline.toString();
		}
	}
	
	private class Airport {
		
		private String LocationCode;
		
		public String toString() { 
			return LocationCode;
		}
	}
	
	private class Airline {
		
		private String Code;
		
		public String toString() {
			return Code;
		}
	}
	
	private class AirItineraryPricingInfo {
		
		private ItinTotalFare ItinTotalFare;
		
		public String toString() {
			return ItinTotalFare.toString();
		}
	}
	
	private class ItinTotalFare {
		
		private Fare TotalFare;
		
		public String toString() {
			return TotalFare.toString();
		}
	}
	
	private class Fare {
		
		private String CurrencyCode;
		private String Amount;
		
		public String toString() {
			return "$" + Amount + " (" + CurrencyCode + ")";
		}
		
		public Double getAmount() {
			return Double.valueOf(Amount);
		}
	}
	
}
