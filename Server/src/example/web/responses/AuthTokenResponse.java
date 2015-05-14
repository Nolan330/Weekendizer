package example.web.responses;

public class AuthTokenResponse {
	
	public String access_token;
	public String token_type;
	public String expires_in;
	
	public AuthTokenResponse(String aToken, String tType, String expires) {
		access_token = aToken;
		token_type = tType;
		expires_in = expires;
	}
	
}
