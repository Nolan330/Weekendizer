package example.web.responses;

import java.util.List;

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
	 * Selects NUM_PLACES places at random
	 */
	public List<Place> getRandomPlaces() {
		return mPlaces.subList(0, NUM_PLACES);
	}
	
}
