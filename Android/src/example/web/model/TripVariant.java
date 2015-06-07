package example.web.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * A POJO used in the WeekendPlannerResponse to represent
 * different variations of things to do, selectable by the user
 * based on personal preferences
 */
public class TripVariant {
	
	/**
	 * The current budget remaining for this variant
	 */
	@SerializedName("currentBudget")
	private Double mCurrentBudget;
	
	/**
	 * The schedule of events for this variant
	 */
	@SerializedName("schedule")
	private List<Event> mSchedule;
	
	/**
	 * A List of lists of places that represent things to do
	 * that don't necessarily require tickets. Each item in
	 * the outer list represents a day, and each inner list
	 * represents place suggestions for that day based on the weather
	 */
	@SerializedName("places")
	private List<List<Place>> mPlaces;
	
	public List<Event> getSchedule() {
		return mSchedule;
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
