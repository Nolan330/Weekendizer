package example.weekendplanner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.function.Supplier;

import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;
import retrofit.client.UrlConnectionClient;
import example.web.model.City;
import example.web.requests.WeekendPlannerRequest;
import example.web.responses.CityInfoResponse;
import example.web.services.FlightInfoService;
import example.web.services.PlacesInfoService;
import example.web.services.TicketInfoService;
import example.web.services.WeatherInfoService;
import example.web.utils.AuthUtils;

public class WeekendPlannerOps {
	
	/**
	 * Default values
	 */
	private final int THREAD_COUNT = 8;
	private final int NUM_TRIP_VARIANTS = 5;
	
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
	 * Retrofit adapters for interacting with the various APIs
	 */
	private final String mFlightInfoEndpoint = "https://api.test.sabre.com/";
	private FlightInfoService mFlightInfoService;
	
	private final String mTicketInfoEndpoint = "https://api.stubhubsandbox.com/";
	private TicketInfoService mTicketInfoService;
	
	private final String mWeatherInfoEndpoint = "api.openweathermap.org/";
	private WeatherInfoService mWeatherInfoService;
	
	private final String mPlacesInfoEndpoint = "placeholder.ignore.eu"; 
	private PlacesInfoService mPlacesInfoService;
	
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

		mFlightInfoService = makeService(
			mFlightInfoEndpoint, FlightInfoService.class);
		
		mTicketInfoService = makeService(
			mTicketInfoEndpoint, TicketInfoService.class);
		
		mWeatherInfoService = makeService(
			mWeatherInfoEndpoint, WeatherInfoService.class);
		
		mPlacesInfoService = makeService(
			mPlacesInfoEndpoint, PlacesInfoService.class);
	}
	
	private <T> T makeService(String endpoint, Class<T> serviceClass) {
		return new RestAdapter.Builder()
			.setClient(new UrlConnectionClient())
			.setEndpoint(endpoint)
			.setLogLevel(LogLevel.FULL)
			.build()
			.create(serviceClass);
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
			() -> mFlightInfoService.authorize(
				AuthUtils.getFlightCredential(),
				AuthUtils.getFlightGrantType()).access_token,
			getExecutor());
	}
	
	@SuppressWarnings("unused")
	private <T> CompletableFuture<T> supplyCompletableFuture(Supplier<T> supplier) {
		return CompletableFuture.supplyAsync(
			supplier,
			getExecutor());
	}
	
	public CompletableFuture<String> getTicketAuthToken() {
		return CompletableFuture.supplyAsync(
			() -> mTicketInfoService.authorize(
				AuthUtils.getTicketCredential(),
				AuthUtils.getTicketGrantType(),
				AuthUtils.getTicketUsername(),
				AuthUtils.getTicketPassword(),
				AuthUtils.getTicketScope()).access_token,
			getExecutor());
	}
	
	public CompletableFuture<CityInfoResponse> getCities(
			String authToken, String country) {
		return CompletableFuture.supplyAsync(
			() -> mFlightInfoService.queryCities(
				"Bearer " + authToken,
				country),
			getExecutor());
	}

	public CompletableFuture<List<TripVariant>> getFlight(String authToken) {
		// check if cur/dest is same ("find things to do")
		// then don't make query just set remaining = budget;
		// otherwise make API request to get list of flights
		// create/update remainingBudget vector?
		// or like weekend vector .setRemainingBudget() values
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
