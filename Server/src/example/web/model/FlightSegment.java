package example.web.model;

import java.util.List;

public class FlightSegment {
	
	public List<Segment> FlightSegment;
	public Integer ElapsedTime;
	
	public String toString() {
		String segments = "";
		for(Segment s : FlightSegment) {
			segments += s.toString() + ", ";
		}
		return "[" + segments + "] taking " + ElapsedTime + " minutes";
	}

}
