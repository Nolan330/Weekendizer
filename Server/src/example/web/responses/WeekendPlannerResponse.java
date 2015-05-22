package example.web.responses;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import example.web.ops.WeekendPlannerOps.Event;

public class WeekendPlannerResponse {
	
	public List<TripVariant> TripVariants;
	private final Double InitBudget;
	private FlightInfoResponse mFlight;
	
	public WeekendPlannerResponse(Double initBudget, int numVariants) {
		InitBudget = initBudget;
		TripVariants = 
			Stream.generate(() -> new TripVariant(InitBudget))
				.limit(numVariants)
				.collect(Collectors.toList());
	}
	
	public Double getInitialBudget() {
		return InitBudget;
	}
	
	public Double getBudgetAfterFlight() {
		return InitBudget - mFlight.getFare();
	}
	
	public FlightInfoResponse getFlight() {
		return mFlight;
	}
	
	public LocalDateTime getDepartureDateTime() {
		return mFlight.getDepartureDateTime();
	}
	
	public LocalDateTime getReturnDateTime() {
		return mFlight.getReturnDateTime();
	}
	
	public WeekendPlannerResponse update(FlightInfoResponse response) {
		mFlight = response;
		TripVariants.parallelStream()
			.forEach(tV -> tV.subtractFromBudget(response.getFare()));
		return this;
	}
	
	public WeekendPlannerResponse update(TicketInfoResponse response) {
		Random r = new Random();
		// much more involved
		TripVariants.parallelStream()
			.forEach(tV -> tV.subtractFromBudget(r.nextDouble() * 105 + 1));
		TripVariants.forEach(System.out::println);
		return this;
	}

	public class TripVariant {
		
		private Double mCurrentBudget;
		private List<Event> mFridayPlan;
		private List<Event> mSaturdayPlan;
		private List<Event> mSundayPlan;
		
		public TripVariant(Double currentBudget) {
			mCurrentBudget = currentBudget;
		}
		
		public Double getRemainingBudget() {
			return mCurrentBudget;
		}
		
		public Double subtractFromBudget(Double amount) {
			return mCurrentBudget -= amount;
		}
		
		public String toString() {
			return "{Current Budget: " + mCurrentBudget + "}";
		}
	}

}
