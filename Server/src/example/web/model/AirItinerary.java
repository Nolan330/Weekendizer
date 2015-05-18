package example.web.model;

public class AirItinerary {
	
	public OriginDestinationOptions OriginDestinationOptions;
	
	public AirItinerary(OriginDestinationOptions originDestinationOptions) {
		OriginDestinationOptions = originDestinationOptions;
	}
	
	public String toString() {
		return this.OriginDestinationOptions.toString();
	}
	
}
