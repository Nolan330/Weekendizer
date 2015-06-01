package example.web.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Weather {
	
	@SerializedName("temp")
	private Temperature mTemperature;
	
	@SerializedName("weather")
	private List<Condition> mConditions;
	
	public Double getDayTemperature() {
		return mTemperature.getDayTemperature();
	}
	
	public String getDayCondition() {
		return mConditions.get(0).getCondition();
	}
	
	@Override
	public String toString() {
		return "Temperature: " + mTemperature + "F, Conditions:" + mConditions;
	}

	public class Temperature {
		
		@SerializedName("day")
		private Double mDayTemperature;
		
		public Double getDayTemperature() {
			return mDayTemperature;
		}
		
		public String toString() {
			return mDayTemperature.toString();
		}
	}
	
	public class Condition {
		
		@SerializedName("main")
		private String mCondition;
		
		public String getCondition() {
			return mCondition;
		}
		
		public String toString() {
			return mCondition;
		}
	}
}
