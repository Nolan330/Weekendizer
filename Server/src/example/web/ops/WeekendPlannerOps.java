package example.web.ops;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import example.web.model.City;
import example.web.requests.WeekendPlannerRequest;
import example.web.responses.CityInfoResponse;
import example.web.responses.FlightInfoResponse;

public class WeekendPlannerOps {
	
	/**
	 * Default values
	 */
	private final int THREAD_COUNT = 8;
	private final int NUM_TRIP_VARIANTS = 5;
	private final String FLIGHT_ENDPOINT = "https://api.test.sabre.com/";
	private final String TICKET_ENDPOINT = "https://api.stubhubsandbox.com/";
	private final String WEATHER_ENDPOINT = "api.openweathermap.org/";
	private final String PLACES_ENDPOINT = "placeholder.ignore.eu/";
	
	private Executor mExecutor;
	
	/**
	 * Information about the trip from the request
	 */
	private City mCurrentCity;
	private City mDestinationCity;
	private Double mBudget;
	
	/**
	 * State used in trip calculations
	 */
	private List<TripVariant> mTripVariants;
	
	/**
	 * Helper classes for interacting with the various APIs
	 */
	private FlightOps mFlightOps;
	private TicketOps mTicketOps;
	private WeatherOps mWeatherOps;
	private PlacesOps mPlacesOps;
	
	public WeekendPlannerOps() {
		init(null, makeDefaultExecutor(THREAD_COUNT));
	}
	
	public WeekendPlannerOps(WeekendPlannerRequest req) {
		init(req, makeDefaultExecutor(THREAD_COUNT));
	}
	
	public WeekendPlannerOps(WeekendPlannerRequest req, int numThreads) {
		init(req, makeDefaultExecutor(numThreads));
	}
	
	public WeekendPlannerOps(WeekendPlannerRequest req, Executor executor) {
		init(req, executor);
	}
	
	private void init(WeekendPlannerRequest req, Executor exec) {
		mExecutor = exec;

		if (req != null) {
			mCurrentCity = req.currentCity;
			mDestinationCity = req.destinationCity;
			mBudget = Double.valueOf(req.budget);
		}
		
		mTripVariants = new ArrayList<TripVariant>(NUM_TRIP_VARIANTS);
		
		mFlightOps = new FlightOps(FLIGHT_ENDPOINT);
		mTicketOps = new TicketOps(TICKET_ENDPOINT);
		mWeatherOps = new WeatherOps(WEATHER_ENDPOINT);
		mPlacesOps = new PlacesOps(PLACES_ENDPOINT);
	}
	
	public Executor getExecutor() {
		return mExecutor;
	}
	
	private Executor makeDefaultExecutor(int numThreads) {
		return Executors.newFixedThreadPool(
			numThreads,
			new ThreadFactory() {
				@Override
				public Thread newThread(Runnable r) {
					Thread t = new Thread(r);
					t.setDaemon(true);
					return t;
				}
			});
	}
	
	public CompletableFuture<String> getFlightAuthToken() {
		return CompletableFuture.supplyAsync(
			() -> mFlightOps.authorize(),
			getExecutor());
	}
	
	public CompletableFuture<String> getTicketAuthToken() {
		return CompletableFuture.supplyAsync(
			() -> mTicketOps.authorize(),
			getExecutor());
	}
	
	public CompletableFuture<CityInfoResponse> getCities(
			String authToken, String country) {
		return CompletableFuture.supplyAsync(
			() -> mFlightOps.getCities(authToken, country),
			getExecutor());
	}

	public CompletableFuture<FlightInfoResponse> getFlight(String authToken) {
		return CompletableFuture.supplyAsync(
			() -> mFlightOps.getFlight(
				authToken,
				mCurrentCity.code,
				mDestinationCity.code,
				getFormattedDate(DayOfWeek.FRIDAY),
				getFormattedDate(DayOfWeek.SUNDAY),
				String.valueOf(mBudget)),
			getExecutor());
		// then don't make query just set remaining = budget;
		// otherwise make API request to get list of flights
		// create/update remainingBudget vector?
		// or like weekend vector .setRemainingBudget() values
	}
	
	public CompletableFuture<List<TripVariant>> updateTrip(FlightInfoResponse flightInfoResponse) {
		return null;
	}
	
	public CompletableFuture<List<TripVariant>> getTickets(
			List<TripVariant> tripVariants,
			String authToken) {
		return null;
	}
	
	public CompletableFuture<Weather> getWeather() {
		return null;
	}
	
	public CompletableFuture<List<TripVariant>> fillWeekend(
			CompletableFuture<List<TripVariant>> tripVariants,
			Weather weather) {
		return null;
	}
	
	private String getFormattedDate(DayOfWeek day) {
		LocalDate today = LocalDate.now();
		return (today.getDayOfWeek() == day && day == DayOfWeek.FRIDAY ?
				today : today.with(TemporalAdjusters.next(day)))
			.format(DateTimeFormatter.ISO_LOCAL_DATE);
	}
	
	public class Weather {}
	public class Event {}
	public class TripVariant {
		//departure
		//return
		//price
		//list of events in order by time
		//remainingBudget - "iceCream"
	}

}
