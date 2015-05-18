package example.web.model;

public class Flight {

	public AirItinerary AirItinerary;
	public AirItineraryPricingInfo AirItineraryPricingInfo;
	
	public Flight(AirItinerary airItinerary,
			AirItineraryPricingInfo airItineraryPricingInfo) {
		AirItinerary = airItinerary;
		AirItineraryPricingInfo = airItineraryPricingInfo;
	}
	
	public String toString() {
		return this.AirItinerary.toString()
				+ "costing " + this.AirItineraryPricingInfo.toString();
	}
	
}
