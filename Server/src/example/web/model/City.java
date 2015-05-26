package example.web.model;

import com.google.gson.annotations.SerializedName;

public class City {

	@SerializedName("code")
	private String mCode;
	
	@SerializedName("name")
	private String mName;
	
	public String getCode() {
		return mCode;
	}
	
	public String getName() {
		return mName;
	}
	
}
