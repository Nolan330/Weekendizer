package example.web.responses;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import example.web.model.Geocode;

public class GeoCodeResponse {
	
	@SerializedName("results")
	private List<Geocode> mGeocodes;
	
	public String getLat() {
		return mGeocodes.get(0).getLat();
	}
	
	public String getLng() {
		return mGeocodes.get(0).getLng();
	}

	public String toString() {
		return mGeocodes.toString();
	}
}
