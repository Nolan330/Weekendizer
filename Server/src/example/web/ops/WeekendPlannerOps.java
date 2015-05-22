package example.web.ops;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import example.web.model.City;
import example.web.requests.WeekendPlannerRequest;
import example.web.responses.CityInfoResponse;
import example.web.responses.WeekendPlannerResponse;
import example.web.utils.DateUtils;

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
	
	/**
	 * The executor responsible for scheduling threads
	 */
	private Executor mExecutor;
	
	/**
	 * Information about the trip from the request
	 */
	private City mCurrentCity;
	private City mDestinationCity;
	private Double mBudget;
	
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
	
	public CompletableFuture<WeekendPlannerResponse> initTrip() {
		return CompletableFuture.completedFuture(
			new WeekendPlannerResponse(mBudget, NUM_TRIP_VARIANTS));
	}
	
	public CompletableFuture<String> getFlightAuthToken() {
		return CompletableFuture.supplyAsync(
			() -> mFlightOps.getAuthToken(),
			getExecutor());
	}
	
	public CompletableFuture<CityInfoResponse> getCities(
			String country, String authToken) {
		return CompletableFuture.supplyAsync(
			() -> mFlightOps.getCities(authToken, country),
			getExecutor());
	}

	public CompletableFuture<WeekendPlannerResponse> getFlight(
			WeekendPlannerResponse tripVariants, String authToken) {
		return CompletableFuture.supplyAsync(
				() -> mFlightOps.getFlight(
					authToken,
					mCurrentCity.code,
					mDestinationCity.code,
					DateUtils.getFormattedDateOfNext(DayOfWeek.FRIDAY),
					DateUtils.getFormattedDateOfNext(DayOfWeek.SUNDAY),
					String.valueOf(tripVariants.getInitialBudget())),
				getExecutor())
			// Compare to .thenCompose(tripVariants::update));
			.thenApply(tripVariants::update);
	}
	
	public CompletableFuture<String> getTicketAuthToken() {
		return CompletableFuture.supplyAsync(
			() -> mTicketOps.getAuthToken(),
			getExecutor());
	}
	
	public CompletableFuture<WeekendPlannerResponse> getTickets(
			WeekendPlannerResponse tripVariants, String authToken) {
		Arrays.asList(DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
		return getTicketsForDay(DayOfWeek.FRIDAY, tripVariants, authToken)
			.thenCompose(variants ->
				getTicketsForDay(DayOfWeek.SATURDAY, variants, authToken))
			.thenCompose(variants ->
				getTicketsForDay(DayOfWeek.SUNDAY, variants, authToken));
	}
	
	private CompletableFuture<WeekendPlannerResponse> getTicketsForDay(
			DayOfWeek day, WeekendPlannerResponse tripVariants,
			String authToken) {
		return CompletableFuture.supplyAsync(
				() -> mTicketOps.getTickets(
					authToken,
					DateUtils.makeDateTimeRange(day, tripVariants.getFlight()),
					mDestinationCity.name,
					String.valueOf(tripVariants.getBudgetAfterFlight())),
				getExecutor())
			.thenApply(tripVariants::update);
	}
	
	public CompletableFuture<Weather> getWeather() {
		return null;
	}
	
	public CompletableFuture<List<WeekendPlannerResponse>> fillWeekend(
			WeekendPlannerResponse tripVariants, Weather weather) {
		return null;
	}

	public class Weather {}
	public class Event {}

}
