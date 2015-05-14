package example.web.utils;

import java.util.Base64;
import java.util.Base64.Encoder;

public class AuthUtils {

	private final static String clientID = "V1:1yp9p698tnoko3to:DEVCENTER:EXT";
	private final static String clientSecret = "26ktVdFH";
	private final static Encoder urlEncoder = Base64.getUrlEncoder();
	
	public static String getCredential() {
		String cred =
			urlEncoder.encodeToString(clientID.getBytes())
			+ ":"
			+ urlEncoder.encodeToString(clientSecret.getBytes());
		
		return urlEncoder.encodeToString(cred.getBytes());
	}
	
}
