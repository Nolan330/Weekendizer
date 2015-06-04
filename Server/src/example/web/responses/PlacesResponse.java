package example.web.responses;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.google.gson.annotations.SerializedName;

import example.web.model.Place;

/**
 * The top-level POJO returned by the Google Places API
 */
public class PlacesResponse {
	
	/**
	 * Default values
	 */
	private final Integer NUM_PLACES = 5;
	
	/**
	 * The places returned by the query
	 */
	@SerializedName("results")
	private List<Place> mPlaces;
	
	public List<Place> getPlaces() {
		return mPlaces;
	}
	
	/**
	 * Selects and returns NUM_PLACES places at random in a list
	 */
	public List<Place> getRandomPlaces() {
		return new Random().ints(NUM_PLACES, 0, mPlaces.size() - 1)
			.mapToObj(mPlaces::get)
			.collect(Collectors.toList());
	}
	
}
