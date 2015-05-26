package example.web.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class TripVariant {
	
	@SerializedName("currentBudget")
	private Double mCurrentBudget;
	
	@SerializedName("schedule")
	private List<Event> mSchedule;
	
	public TripVariant(Double currentBudget) {
		mSchedule = new ArrayList<Event>();
		mCurrentBudget = currentBudget;
	}
	
	public List<Event> getSchedule() {
		return mSchedule;
	}
	
	public Boolean addEvent(Event event) {
		if (tooExpensive(event) 
				|| !startsAfterPreviousEvent(event)
				|| alreadyGoing(event))
			return false;
		
		if (mSchedule.add(event)) {
			subtractFromBudget(event.getTicketPrice());
			return true;
		}
		
		return false;
	}
	
	private Boolean tooExpensive(Event e) {
		return e.getTicketPrice().compareTo(mCurrentBudget) > 0;
	}
	
	private Boolean startsAfterPreviousEvent(Event e) {
		return mSchedule.size() == 0 ?
			true : e.getStartDateTime().isAfter(
				mSchedule.get(mSchedule.size() - 1).getEndDateTime());
	}
	
	private Boolean alreadyGoing(Event e) {
		return mSchedule.stream()
			.anyMatch(schedEvent ->
				schedEvent.getTitle().equalsIgnoreCase(e.getTitle()));
	}
	
	public Double getRemainingBudget() {
		return mCurrentBudget;
	}
	
	public Double subtractFromBudget(Double amount) {
		return mCurrentBudget -= amount;
	}
	
	public String toString() {
		return "Remaining Budget: " + mCurrentBudget + ", "
				+ mSchedule.toString() + "}";
	}
	
}
