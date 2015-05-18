package example.web.utils;

import java.util.Base64;
import java.util.Base64.Encoder;

public abstract class BaseOAuth2Utils {
	
	private final static Encoder mUrlEncoder = Base64.getUrlEncoder();
	
	protected String mClientKey;
	protected String mClientSecret;
	protected Boolean mIsPreEncoded;
	protected String mGrantType;
	protected String mUsername;
	protected String mPassword;
	protected String mScope;
	
	public String makeCredential() {
		String cred =
			(!mIsPreEncoded ? 
				mUrlEncoder.encodeToString(mClientKey.getBytes())
				: mClientKey)
			+ ":"
			+ (!mIsPreEncoded ? 
				mUrlEncoder.encodeToString(mClientSecret.getBytes())
				: mClientSecret);
			
		return "Basic " + mUrlEncoder.encodeToString(cred.getBytes());
	}
	
	public String makeBearerToken(String authToken) {
		return "Bearer " + authToken;
	}
	
	public String getGrantType() {
		return mGrantType;
	}
	
	public String getUsername() {
		return mUsername;
	}
	
	public String getPassword() {
		return mPassword;
	}
	
	public String getScope() {
		return mScope;
	}

}
