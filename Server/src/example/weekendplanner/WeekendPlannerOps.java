package example.weekendplanner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;
import retrofit.client.UrlConnectionClient;
import example.web.model.City;
import example.web.requests.WeekendPlannerRequest;
import example.web.responses.CityInfoResponse;
import example.web.services.FlightInfoService;
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
	 * Retrofit adaptor for interacting with the flight API
	 */
	private FlightInfoService mFlightInfoService;
	
	public WeekendPlannerOps() {
		init(null, getDefaultExecutor(THREAD_COUNT));
	}
	
	public WeekendPlannerOps(WeekendPlannerRequest req) {
		init(req, getDefaultExecutor(THREAD_COUNT));
	}
	
	public WeekendPlannerOps(WeekendPlannerRequest req, int numThreads) {
		init(req, getDefaultExecutor(numThreads));
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

		RestAdapter flightInfoAdapter =
    		new RestAdapter.Builder()
				.setClient(new UrlConnectionClient())
				.setEndpoint("https://api.test.sabre.com/")
				.setLogLevel(LogLevel.FULL)
				.build();

		mFlightInfoService =
			flightInfoAdapter.create(FlightInfoService.class);

		mTripVariants = new ArrayList<TripVariant>(NUM_TRIP_VARIANTS);
	}
	
	public Executor getExecutor() {
		return mExecutor;
	}
	
	private Executor getDefaultExecutor(int numThreads) {
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
				"Basic " + AuthUtils.getCredential(),
				"client_credentials").access_token,
			getExecutor());
	}
	
	public CompletableFuture<String> getEventAuthToken() {
		return null;
	}
	
	public CompletableFuture<String> getTicketAuthToken() {
		return null;
	}
	
	public CompletableFuture<CityInfoResponse> getCities(String authToken,
														 String country) {
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
	
	public CompletableFuture<List<TripVariant>> getEvents(
			List<TripVariant> tripVariants, String authToken) {
		return null;
	}
	
	public CompletableFuture<List<TripVariant>> filterEventsByTicketPrice(
			CompletableFuture<List<TripVariant>> tripVariants,
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
