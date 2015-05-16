package example.web.services;

import example.web.responses.OAuth2TokenResponse;
import example.web.responses.CityInfoResponse;
import example.web.responses.FlightInfoResponse;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;

public interface FlightInfoService {
	
	@FormUrlEncoded
	@POST("/v1/auth/token")
	OAuth2TokenResponse authorize(
		@Header("Authorization") String auth,
		@Field("grant_type") String value);
	
	@GET("/v1/lists/supported/cities")
	CityInfoResponse queryCities(
		@Header("Authorization") String token,
		@Query("country") String country);
	
	@GET("/v1/shop/flights")
	FlightInfoResponse queryFlights(
		 @Header("Authorization") String token,
		 @Query("origin") String origin,
		 @Query("destination") String destination,
		 @Query("departuredate") String departureDate,
		 @Query("returndate") String returnDate,
		 @Query("outbounddeparturewindow") String departureWindow,
		 @Query("inboundarrivalwindow") String returnWindow,
		 @Query("maxfare") String maxFare,
		 @Query("limit") String limitResponses,
		 @Query("order2") String secondaryOrder,
		 @Query("sortby2") String secondarySort);
									
}
