package example.web.model;

import com.google.gson.annotations.SerializedName;

public class Geocode {
	
	@SerializedName("geometry")
	private Geometry mGeometry;
	
	public String getLat() {
		return mGeometry.getLat();
	}
	
	public String getLng() {
		return mGeometry.getLng();
	}
	
	public String toString() {
		return "{" + getLat() + "," + getLng() + "}";
	}
	
	public class Geometry {
		
		@SerializedName("location")
		private LatLng mLocation;
		
		public String getLat() {
			return mLocation.getLat();
		}
		
		public String getLng() {
			return mLocation.getLng();
		}
	}
	
	public class LatLng {
		
		@SerializedName("lat")
		private String mLat;
		
		@SerializedName("lng")
		private String mLng;
		
		public String getLat() {
			return mLat;
		}
		
		public String getLng() {
			return mLng;
		}
	}

}
