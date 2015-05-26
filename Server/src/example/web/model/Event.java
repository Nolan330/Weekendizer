package example.web.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.google.gson.annotations.SerializedName;

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
	
	public Event(String title, String startDateTime,
			String endDateTime, Double fare, String currencyCode) {
		mTitle = title;
		mStartDateTime = startDateTime;
		mEndDateTime = endDateTime;
		mTicketInfo = new TicketInfo(fare, currencyCode);
	}
	
	public String getTitle() {
		return mTitle;
	}
	
	public LocalDate getDate() {
		return getDateTime(mStartDateTime).toLocalDate();
	}
	
	public LocalDateTime getStartDateTime() {
		return getDateTime(mStartDateTime);
	}
	
	public LocalDateTime getEndDateTime() {
		return mEndDateTime == null ?
			getDateTime(mStartDateTime).plusHours(DURATION_HOURS) :
			getDateTime(mEndDateTime);
	}
	
	private LocalDateTime getDateTime(String dateTime) {
		return LocalDateTime.parse(
			dateTime.substring(0, dateTime.lastIndexOf("-")));
	}
	
	public Double getTicketPrice() {
		return mTicketInfo.getPrice();
	}
	
	@Override
	public int hashCode() {
		return getTitle().hashCode() ^ getDate().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Event))
			return false;
		if (obj == this)
			return true;
		
		Event other = (Event) obj;
		return getTitle().equalsIgnoreCase(other.getTitle()) &&
				getDate().equals(other.getDate());
	}
	
	@Override
	public String toString() {
		return mTitle 
			+ " at " + mStartDateTime
			+ " for $" + getTicketPrice();
	}

	@SuppressWarnings("unused")
	public class Venue {
		private String name;
		private String timezone;
		private String address1;
		private String city;
		private String state;
	}
	
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
	}
	
	@SuppressWarnings("unused")
	public class Category {
		private String id;
		private String name;
	}

}
