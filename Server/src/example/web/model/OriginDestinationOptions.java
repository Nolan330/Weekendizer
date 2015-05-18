package example.web.model;

import java.util.List;

public class OriginDestinationOptions {
	
	public List<FlightSegment> OriginDestinationOption;
	
	public OriginDestinationOptions(List<FlightSegment> originDestinationOption) {
		OriginDestinationOption = originDestinationOption;
	}
	
	public String toString() {
		String options = "";
		for(FlightSegment f : OriginDestinationOption) {
			options += f.toString() + ",\n";
		}
		return options;
	}
	
}
