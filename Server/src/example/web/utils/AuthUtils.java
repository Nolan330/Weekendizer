package example.web.utils;

import java.util.Base64;
import java.util.Base64.Encoder;

public class AuthUtils {
	
	private final static Encoder urlEncoder = Base64.getUrlEncoder();

	private final static String flightClientID = "V1:1yp9p698tnoko3to:DEVCENTER:EXT";
	private final static String flightClientSecret = "26ktVdFH";
	private final static String flightGrantType = "client_credentials";
	
	public static String getFlightCredential() {
		return makeCredential(flightClientID, flightClientSecret, true);
	}
	
	public static String getFlightGrantType() {
		return flightGrantType;
	}
	
	private final static String ticketClientID = "4zwpG09zanoQX79_SyuqqbgAsEIa";
	private final static String ticketClientSecret = "9tDFugiYuan5W9EgCmHFhj_Wm0sa";
	private final static String ticketUsername = "nolan.m.smith@vanderbilt.edu";
	private final static String ticketPassword = "testPass123";
	private final static String ticketGrantType = "password";
	private final static String ticketScope = "SANDBOX";
	
	public static String getTicketCredential() {
		return makeCredential(ticketClientID, ticketClientSecret, false);
	}
	
	public static String getTicketUsername() {
		return ticketUsername;
	}
	
	public static String getTicketPassword() {
		return ticketPassword;
	}
	
	public static String getTicketGrantType() {
		return ticketGrantType;
	}
	
	public static String getTicketScope() {
		return ticketScope;
	}
	
	private static String makeCredential(
			String clientKey, String clientSecret, boolean doEncodeParts) {
		String cred =
			(doEncodeParts ? 
				urlEncoder.encodeToString(clientKey.getBytes())
				: clientKey)
			+ ":"
			+ (doEncodeParts ? 
				urlEncoder.encodeToString(clientSecret.getBytes())
				: clientSecret);
			
		return "Basic " + urlEncoder.encodeToString(cred.getBytes());
	}
	
}
