package example.utils;

import android.util.Base64;

public class AuthUtils {

	private final static String clientID = "V1:1yp9p698tnoko3to:DEVCENTER:EXT";
	private final static String clientSecret = "26ktVdFH";
	
	public static String getCredential() {
		String cred =
			Base64.encodeToString(
				clientID.getBytes(), Base64.DEFAULT | Base64.NO_WRAP)
			+ ":"
			+ Base64.encodeToString(clientSecret.getBytes(), Base64.DEFAULT);

		return Base64.encodeToString(cred.getBytes(), Base64.DEFAULT);
	}
	
}
