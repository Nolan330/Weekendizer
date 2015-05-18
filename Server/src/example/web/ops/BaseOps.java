package example.web.ops;

import java.io.IOException;
import java.net.HttpURLConnection;

import example.web.utils.NoAuthUtils;
import example.web.utils.BaseOAuth2Utils;
import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;
import retrofit.client.Request;
import retrofit.client.UrlConnectionClient;

public abstract class BaseOps<T> {
	
	protected String mEndpoint;
	protected T mService;
	protected BaseOAuth2Utils mAuthUtils;
	
	protected BaseOps(String endpoint, Class<T> serviceClass) {
		init(endpoint, serviceClass, null);
	}
	
	protected BaseOps(String endpoint, 
			Class<T> serviceClass, BaseOAuth2Utils authUtils) {
		init(endpoint, serviceClass, authUtils);
	}
	
	private void init(String endpoint, Class<T> serviceClass,
			BaseOAuth2Utils authUtils) {
		mEndpoint = endpoint;
		mService = makeService(serviceClass);
		mAuthUtils = authUtils != null ? authUtils : new NoAuthUtils();
	}
	
	public String getEndpoint() {
		return mEndpoint;
	}
	
	// TODO: deal with API-specific token refreshing
	public abstract String authorize();
	
	public BaseOAuth2Utils getAuthUtils() {
		return mAuthUtils;
	}
	
	public T getService() {
		return mService;
	}
	
	private T makeService(Class<T> serviceClass) {
		return new RestAdapter.Builder()
			.setClient(new UrlConnectionClient())
			.setEndpoint(mEndpoint)
			.setLogLevel(LogLevel.FULL)
			.build()
			.create(serviceClass);
	}

}
