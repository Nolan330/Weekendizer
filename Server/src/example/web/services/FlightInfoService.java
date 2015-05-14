package example.web.services;

import example.web.responses.AuthTokenResponse;
import example.web.responses.CityInfoResponse;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;

public interface FlightInfoService {
	
	@FormUrlEncoded
	@POST("/v1/auth/token")
	AuthTokenResponse authorize(@Header("Authorization") String auth,
								@Field("grant_type") String value);
	
	@GET("/v1/lists/supported/cities")
	CityInfoResponse queryCities(@Header("Authorization") String token,
 					   			 @Query("country") String country);
	
	//FlightInfoResponse queryFlights()

}
