package example.web.services;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Header;
import retrofit.http.POST;
import example.web.responses.OAuth2TokenResponse;

public interface TicketInfoService {
	
	@FormUrlEncoded
	@POST("/login")
	OAuth2TokenResponse authorize(
		@Header("Authorization") String auth,
		@Field("grant_type") String grantType,
		@Field("username") String username,
		@Field("password") String password,
		@Field("scope") String scope);

}
