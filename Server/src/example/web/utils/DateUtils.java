package example.web.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

import example.web.responses.FlightInfoResponse;

public class DateUtils {
	
	/**
	 * Default values
	 */
	private static final LocalTime START_OF_DAY = LocalTime.MIN;
	private static final LocalTime END_OF_DAY = LocalTime.MAX;
	
	public static String getFormattedDateOfNext(DayOfWeek day) {
		return getDateOfNext(day)
			.format(DateTimeFormatter.ISO_LOCAL_DATE);
	}
	
	private static LocalDate getDateOfNext(DayOfWeek day) {
		LocalDate today = LocalDate.now();
		return today.getDayOfWeek() == day && day == DayOfWeek.FRIDAY ?
			today : today.with(TemporalAdjusters.next(day));
	}
	
	public static String makeDateTimeRange(
			DayOfWeek day, FlightInfoResponse flight) {
		LocalDate date = getDateOfNext(day);
		switch(day) {
		case FRIDAY:
			return formatDateTimeRange(
				date,
				flight.getDepartureDateTime().toLocalTime(),
				END_OF_DAY);
		case SATURDAY:
			return formatDateTimeRange(
				date,
				START_OF_DAY,
				END_OF_DAY);
		case SUNDAY:
			return formatDateTimeRange(
				date,
				START_OF_DAY,
				flight.getReturnDateTime().toLocalTime().minusHours(1));
		default:
			return null;
		}
	}
	
	private static String formatDateTimeRange(
			LocalDate date, LocalTime start, LocalTime end) {
		return removeSeconds(date.atTime(start).format(
				DateTimeFormatter.ISO_LOCAL_DATE_TIME))
			+ " TO "
			+ removeSeconds(date.atTime(end).format(
				DateTimeFormatter.ISO_LOCAL_DATE_TIME));
	}
	
	private static String removeSeconds(String dateTime) {
		return dateTime.substring(0, dateTime.lastIndexOf(":"));
	}
	
}
