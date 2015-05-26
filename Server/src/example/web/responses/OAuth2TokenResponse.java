package example.web.responses;

import com.google.gson.annotations.SerializedName;

public class OAuth2TokenResponse {
	
	@SerializedName("access_token")
	private String mAccessToken;
	
	@SerializedName("token_type")
	private String mTokenType;
	
	@SerializedName("expires_in")
	private String mExpiresIn;
	
	public OAuth2TokenResponse(String credential) {
		mAccessToken = credential;
	}
	
	public String getAccessToken() {
		return mAccessToken;
	}
	
	public String toString() {
		return mAccessToken
				+ " {type: " + mTokenType
				+ ", expires: " + mExpiresIn + "}";
	}
	
}
