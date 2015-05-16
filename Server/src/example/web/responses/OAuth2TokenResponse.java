package example.web.responses;

public class OAuth2TokenResponse {
	
	public String access_token;
	public String token_type;
	public String expires_in;
	
	public OAuth2TokenResponse(String aToken, String tType, String expires) {
		access_token = aToken;
		token_type = tType;
		expires_in = expires;
	}
	
}
