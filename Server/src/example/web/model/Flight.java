package example.web.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Flight {
	
	/**
	 * Member variables from serialization
	 */
	@SerializedName("AirItinerary")
	private AirItinerary mAirItinerary;
	
	@SerializedName("AirItineraryPricingInfo")
	private AirItineraryPricingInfo mAirItineraryPricingInfo;
	
	public Double getFare() {
		return mAirItineraryPricingInfo.getFare();
	}
	
	public String getCurrencyCode() {
		return mAirItineraryPricingInfo.getCurrencyCode();
	}
	
	public String getDepartingDepartureDateTime() {
		return mAirItinerary.getDepartingDepartureDateTime();
	}
	
	public String getDepartingArrivalDateTime() {
		return mAirItinerary.getDepartingArrivalDateTime();
	}
	
	public String getReturningDepartureDateTime() {
		return mAirItinerary.getReturningDepartureDateTime();
	}
	
	public String getReturningArrivalDateTime() {
		return mAirItinerary.getReturningArrivalDateTime();
	}
	
	public String toString() {
		return mAirItinerary + "\n\tcosting " + mAirItineraryPricingInfo;
	}
	
	private class AirItinerary {
		
		@SerializedName("OriginDestinationOptions")
		private OriginDestinationOptions mOriginDestinationOptions;
		
		public String getDepartingDepartureDateTime() {
			return mOriginDestinationOptions.getDepartingDepartureDateTime();
		}
		
		public String getDepartingArrivalDateTime() {
			return mOriginDestinationOptions.getDepartingArrivalDateTime();
		}
		
		public String getReturningDepartureDateTime() {
			return mOriginDestinationOptions.getReturningDepartureDateTime();
		}
		
		public String getReturningArrivalDateTime() {
			return mOriginDestinationOptions.getReturningArrivalDateTime();
		}
	
		public String toString() {
			return mOriginDestinationOptions.toString();
		}
	}
	
	private class OriginDestinationOptions {
		
		@SerializedName("OriginDestinationOption")
		private List<FlightSegment> mOriginDestinationOption;
		
		public String getDepartingDepartureDateTime() {
			return mOriginDestinationOption.get(0)
				.getDepartingDepartureDateTime();
		}
		
		public String getDepartingArrivalDateTime() {
			return mOriginDestinationOption.get(0)
				.getDepartingArrivalDateTime();
		}
		
		public String getReturningDepartureDateTime() {
			return mOriginDestinationOption.get(
					mOriginDestinationOption.size() - 1)
				.getReturningDepartureDateTime();
		}
		
		public String getReturningArrivalDateTime() {
			return mOriginDestinationOption.get(
					mOriginDestinationOption.size() - 1)
				.getReturningArrivalDateTime();
		}
		
		public String toString() {
			String options = "";
			for(FlightSegment f : mOriginDestinationOption) {
				options += "\n" + f + ",";
			}
			return options;
		}
	}
	
	private class FlightSegment {
		
		@SerializedName("FlightSegment")
		private List<Segment> mFlightSegment;
		
		@SerializedName("ElapsedTime")
		private Integer mElapsedTime;
		
		public String getDepartingDepartureDateTime() {
			return mFlightSegment.get(0).getDepartureDateTime();
		}
		
		public String getDepartingArrivalDateTime() {
			return mFlightSegment.get(0).getArrivalDateTime();
		}
		
		public String getReturningDepartureDateTime() {
			return mFlightSegment.get(
				mFlightSegment.size() - 1).getDepartureDateTime();
		}
		
		public String getReturningArrivalDateTime() {
			return mFlightSegment.get(
				mFlightSegment.size() - 1).getArrivalDateTime();
		}
		
		public String toString() {
			String segments = "";
			for(Segment s : mFlightSegment) {
				segments += s.toString() + ", ";
			}
			return "\t[" + segments + "] taking " + mElapsedTime + " minutes";
		}
	}
	
	private class Segment {
		
		@SerializedName("DepartureAirport")
		private Airport mDepartureAirport;
		
		@SerializedName("ArrivalAirport")
		private Airport mArrivalAirport;
		
		@SerializedName("ElapsedTime")
		private Integer mElapsedTime;
		
		@SerializedName("DepartureDateTime")
		private String mDepartureDateTime;
		
		@SerializedName("ArrivalDateTime")
		private String mArrivalDateTime;
		
		@SerializedName("FlightNumber")
		private Integer mFlightNumber;
		
		@SerializedName("OperatingAirline")
		private Airline mOperatingAirline;
		
		public String getArrivalDateTime() {
			return mArrivalDateTime;
		}
		
		public String getDepartureDateTime() {
			return mDepartureDateTime;
		}
		
		public String toString() {
			return mDepartureAirport.toString()
					+ " on " + mDepartureDateTime + " to "
					+ mArrivalAirport.toString()
					+ " on " + mArrivalDateTime
					+ " (" + mElapsedTime + " minutes)"
					+ " via flight #" + mFlightNumber
					+ " with " + mOperatingAirline.toString();
		}
	}
	
	private class Airport {
		
		@SerializedName("LocationCode")
		private String mLocationCode;
		
		public String toString() { 
			return mLocationCode;
		}
	}
	
	private class Airline {
		
		@SerializedName("Code")
		private String mCode;
		
		public String toString() {
			return mCode;
		}
	}
	
	private class AirItineraryPricingInfo {
		
		@SerializedName("ItinTotalFare")
		private ItinTotalFare mItinTotalFare;
		
		public Double getFare() {
			return mItinTotalFare.getFare();
		}
		
		public String getCurrencyCode() {
			return mItinTotalFare.getCurrencyCode();
		}
		
		public String toString() {
			return mItinTotalFare.toString();
		}
	}
	
	private class ItinTotalFare {
		
		@SerializedName("TotalFare")
		private Fare mTotalFare;
		
		public Double getFare() {
			return mTotalFare.getAmount();
		}
		
		public String getCurrencyCode() {
			return mTotalFare.getCurrencyCode();
		}
		
		public String toString() {
			return mTotalFare.toString();
		}
	}
	
	private class Fare {
		
		@SerializedName("Amount")
		private String mAmount;
		
		@SerializedName("CurrencyCode")
		private String mCurrencyCode;
		
		public Double getAmount() {
			return Double.valueOf(mAmount);
		}
		
		public String getCurrencyCode() {
			return mCurrencyCode;
		}
		
		public String toString() {
			return "$" + mAmount + " (" + mCurrencyCode + ")";
		}
	}
	
}
