package example.web.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

import example.web.model.Flight;

public class DateUtils {
	
	public static String getFormattedDateOfNext(DayOfWeek day) {
		return getDateOfNext(day)
			.format(DateTimeFormatter.ISO_LOCAL_DATE);
	}
	
	private static LocalDate getDateOfNext(DayOfWeek day) {
		LocalDate today = LocalDate.now();
		return day == DayOfWeek.FRIDAY ?
			today.with(TemporalAdjusters.nextOrSame(day)) :
			today.plusDays(1).with(TemporalAdjusters.next(day));
	}
	
	public static String makeDateTimeRange(Flight flight) {
		return removeSeconds(flight.getDepartingArrivalDateTime())
			+ " TO "
			+ removeSeconds(flight.getReturningDepartureDateTime());
	}
	
	private static String removeSeconds(String dateTime) {
		return dateTime.substring(0, dateTime.lastIndexOf(":"));
	}
	
}
