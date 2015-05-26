package example.web.utils;

import java.util.Base64;
import java.util.Base64.Encoder;

public abstract class BaseOAuth2Utils {
	
	private final static Encoder mBase64Encoder = Base64.getEncoder();
	public final static int APP_TOKEN = 0;
	public final static int USER_TOKEN = 1;
	
	protected String mClientKey;
	protected String mClientSecret;
	protected String mApplicationToken;
	protected Boolean mIsPreEncoded;
	protected String mGrantType;
	protected String mUsername;
	protected String mPassword;
	protected String mScope;
	
	public String makeCredential(Integer tokenType) {
		switch(tokenType) {
		case APP_TOKEN:
			return !mIsPreEncoded ? 
				mBase64Encoder.encodeToString(mApplicationToken.getBytes())
				: mApplicationToken;
		case USER_TOKEN:
			String cred =
				(!mIsPreEncoded ? 
					mBase64Encoder.encodeToString(mClientKey.getBytes())
					: mClientKey)
				+ ":"
				+ (!mIsPreEncoded ? 
					mBase64Encoder.encodeToString(mClientSecret.getBytes())
					: mClientSecret);
			
			return "Basic " + mBase64Encoder.encodeToString(cred.getBytes());
		default:
			// throw tokenTypeNotSupported Exception
			return null;
		}
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
