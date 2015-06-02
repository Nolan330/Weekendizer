package example.web.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import example.web.model.Geocode.Geometry;

public class Place {
	
	@SerializedName("geometry")
	private Geometry mLocationCoords;
	
	@SerializedName("name")
	private String mName;

	@SerializedName("types")
	private List<String> mTypes;
	
	public String getName() {
		return mName;
	}
	
	public String getType() {
		return mTypes.get(0);
	}
	
}
