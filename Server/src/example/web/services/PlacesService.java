package example.web.services;

import example.web.responses.GeoCodeResponse;
import example.web.responses.PlacesResponse;
import retrofit.http.GET;
import retrofit.http.Query;

public interface PlacesService {
	
	@GET("/maps/api/place/nearbysearch/json")
	PlacesResponse queryPlaces(
		@Query("key") String authToken,
		@Query("location") String latLngCoords,
		@Query("radius") Integer meters,
		@Query("types") String types);
	
	@GET("/maps/api/geocode/json")
	GeoCodeResponse queryGeocode(
		@Query("key") String authToken,
		@Query("address") String city);

}
