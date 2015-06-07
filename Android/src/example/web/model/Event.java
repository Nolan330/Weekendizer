package example.web.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * A POJO representing the fields of a StubHub event necessary for
 * weekend planning
 */
public class Event {

	/**
	 * Default Values
	 */
	private static final Integer DURATION_HOURS = 2;
	
	/**
	 * Member variables tied to serialization
	 */
	@SerializedName("title")
	private String mTitle;
	
	@SerializedName("dateLocal")
	private String mStartDateTime;
	
	@SerializedName("venue")
	private Venue mVenue;
	
	@SerializedName("ticketInfo")
	private TicketInfo mTicketInfo;
	
	@SerializedName("categories")
	private List<Category> mCategories;
	
	@SerializedName("groupings")
	private List<Category> mGroupings;
	
	/**
	 * Member variables maintained by the class
	 */
	private String mEndDateTime;
	
	public String getTitle() {
		return mTitle;
	}
	
	public Double getTicketPrice() {
		return mTicketInfo.getPrice();
	}
	
	@Override
	public String toString() {
		return mTitle 
			+ " at " + mStartDateTime
			+ " for " + mTicketInfo
			+ " located at " + mVenue;
	}

	/**
	 * Nested POJO upon which the Event POJO relies
	 */
	public class Venue {
		@SerializedName("name")
		private String mName;
		
		@SerializedName("timezone")
		private String mTimezone;
		
		@SerializedName("address1")
		private String mStreetAddress;
		
		@SerializedName("city")
		private String mCity;
		
		@SerializedName("state")
		private String mState;
		
		@Override
		public String toString() {
			return mName + ", " 
					+ mStreetAddress + " "
					+ mCity + ", " 
					+ mState;
		}
	}
	
	/**
	 * Nested POJO upon which Event relies
	 */
	public class TicketInfo {
		@SerializedName("minPrice")
		private String mMinPrice;

		@SerializedName("currencyCode")
		private String mCurrencyCode;
		
		public TicketInfo(Double price, String code) {
			mMinPrice = String.valueOf(price);
			mCurrencyCode = code;
		}
		
		public Double getPrice() {
			return Double.valueOf(mMinPrice);
		}
		
		@Override
		public String toString() {
			return "$" + mMinPrice + " " + mCurrencyCode;
		}
	}
	
	/**
	 * Nested POJO upon which Event relies
	 */
	public class Category {
		@SerializedName("id")
		private String mCategoryId;
		
		@SerializedName("name")
		private String mName;
		
		public String toString() {
			return mName;
		}
	}

}
