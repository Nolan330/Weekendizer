package example.web.responses;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import example.web.model.Place;

public class PlacesResponse {
	
	/**
	 * Default values
	 */
	private final Integer NUM_PLACES = 5;
	
	@SerializedName("results")
	private List<Place> mPlaces;
	
	public List<Place> getPlaces() {
		return mPlaces;
	}
	
	public List<Place> getRandomPlaces() {
		return mPlaces.subList(0, NUM_PLACES);
	}
	
}
