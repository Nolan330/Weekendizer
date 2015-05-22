package example.web.responses;

public class OAuth2TokenResponse {
	
	private String access_token;
	private String token_type;
	private String expires_in;
	
	public OAuth2TokenResponse(String credential) {
		access_token = credential;
	}
	
	public String getAccessToken() {
		return access_token;
	}
	
}
