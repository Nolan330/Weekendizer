package example.web.ops;

import java.time.DayOfWeek;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;

import example.web.model.Weather;
import example.web.requests.WeekendPlannerRequest;
import example.web.responses.CityResponse;
import example.web.responses.GeoCodeResponse;
import example.web.responses.WeekendPlannerResponse;
import example.web.responses.PlacesResponse;
import example.web.utils.DateUtils;

public class WeekendPlannerOps {
	
	/**
	 * Default values
	 */
	private final int THREAD_COUNT = 8;
	private final int NUM_TRIP_VARIANTS = 5;
	private final String FLIGHT_ENDPOINT = "https://api.test.sabre.com/";
	private final String TICKET_ENDPOINT = "https://api.stubhubsandbox.com/";
	private final String WEATHER_ENDPOINT = "http://api.openweathermap.org/";
	private final String PLACES_ENDPOINT = "https://maps.googleapis.com/";
	
	/**
	 * The executor responsible for scheduling threads
	 */
	private Executor mExecutor;
	
	/**
	 * Helper classes for interacting with the various APIs
	 */
	private FlightOps mFlightOps;
	private TicketOps mTicketOps;
	private WeatherOps mWeatherOps;
	private PlacesOps mPlacesOps;
	
	public WeekendPlannerOps() {
		init(makeDefaultExecutor(THREAD_COUNT));
	}
	
	public WeekendPlannerOps(WeekendPlannerRequest req, int numThreads) {
		init(makeDefaultExecutor(numThreads));
	}
	
	public WeekendPlannerOps(WeekendPlannerRequest req, Executor executor) {
		init(executor);
	}
	
	private void init(Executor exec) {
		mExecutor = exec;
		
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
	
	// Return a CompletableFuture in order to compose asynchronous computations
	public CompletableFuture<WeekendPlannerResponse> initTrip(
			WeekendPlannerRequest req) {
		return CompletableFuture.completedFuture(
			new WeekendPlannerResponse(
				req.getBudget(),
				req.getOriginCity(),
				req.getDestinationCity(),
				NUM_TRIP_VARIANTS));
	}
	
	public CompletableFuture<String> getFlightAuthToken() {
		return CompletableFuture.supplyAsync(
			() -> mFlightOps.getAuthToken(),
			getExecutor());
	}
	
	public CompletableFuture<CityResponse> getCities(
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
					tripVariants.getOriginCityCode(),
					tripVariants.getDestinationCityCode(),
					DateUtils.getFormattedDateOfNext(DayOfWeek.FRIDAY),
					DateUtils.getFormattedDateOfNext(DayOfWeek.SUNDAY),
					String.valueOf(tripVariants.getInitialBudget())),
				getExecutor())
			// Compare to .thenCompose(tripVariants::update);
			.thenApply(tripVariants::update);
	}
	
	public CompletableFuture<String> getTicketAuthToken() {
		return CompletableFuture.supplyAsync(
			() -> mTicketOps.getAuthToken(),
			getExecutor());
	}
	
	public CompletableFuture<WeekendPlannerResponse> getTickets(
			WeekendPlannerResponse tripVariants, String authToken) {
		// These methods will return a server throttle error because requests are made
		// too quickly
		/**
		List<CompletableFuture<TicketInfoResponse>> responses =
		Arrays.asList(DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY)
			.stream()
			.map(day -> getTicketsForDay(day, tripVariants, authToken))
			.collect(Collectors.toList());
			
		// Apply the update function for each response
		responses.stream()
			.map(CompletableFuture::join)
			.forEach(tripVariants::update);
		
		// Keep interface consistent
		return CompletableFuture.completedFuture(tripVariants);
	----------------------------------------------------------------------------
		return getTicketsForDay(DayOfWeek.FRIDAY, tripVariants, authToken)
			.thenComposeAsync(variants ->
				getTicketsForDay(DayOfWeek.SATURDAY, variants, authToken))
			.thenComposeAsync(variants ->
				getTicketsForDay(DayOfWeek.SUNDAY, variants, authToken));
		*/
		
		// As such, the request cannot be broken into days
		return CompletableFuture.supplyAsync(
				() -> mTicketOps.getTickets(
					authToken,
					DateUtils.makeDateTimeRange(tripVariants.getFlight()),
					tripVariants.getDestinationCityName(),
					String.valueOf(tripVariants.getBudgetAfterFlight())),
				getExecutor())
			.thenApply(tripVariants::update);
	}
	
	public CompletableFuture<WeekendPlannerResponse> getWeather(
			WeekendPlannerResponse tripVariants) {
		return CompletableFuture.supplyAsync(
				() -> mWeatherOps.getWeather(
					tripVariants.getDestinationCityName(),
					DateUtils.getNumDaysUntilNext(DayOfWeek.MONDAY)),
				getExecutor())
			.thenApply(tripVariants::update);
	}
	
	public CompletableFuture<GeoCodeResponse> getGeocode(
			String destinationCityName) {
		return CompletableFuture.supplyAsync(
			() -> mPlacesOps.getGeocode(
				mPlacesOps.getAuthToken(),
				destinationCityName),
			getExecutor());
	}
	
	public CompletableFuture<WeekendPlannerResponse> fillWeekend(
			WeekendPlannerResponse tripVariants, GeoCodeResponse geocode) {
		// Here, the server handles quotas differently, and the server is able to
		// handle day-level requests
		List<CompletableFuture<PlacesResponse>> responses =
		tripVariants.getWeather().stream()
			.map(dayWeather -> getPlacesForDay(dayWeather, geocode))
			.collect(Collectors.toList());
		
		responses.stream()
			.map(CompletableFuture::join)
			.forEach(tripVariants::update);

		return CompletableFuture.completedFuture(tripVariants);
	}
	
	private CompletableFuture<PlacesResponse> getPlacesForDay(
			Weather dayWeather, GeoCodeResponse geocode) {
		return CompletableFuture.supplyAsync(
			() -> mPlacesOps.getPlaces(
				mPlacesOps.getAuthToken(),
				geocode.getLat(),
				geocode.getLng(),
				dayWeather),
			getExecutor());
	}

}
