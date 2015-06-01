package example.web.responses;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import example.web.model.Event;

public class TicketResponse {
	
	@SerializedName("events")
	private List<Event> mEvents;
	
	public List<Event> getEvents() {
		return mEvents;
	}

}
