package uk.ac.dotrural.irp.ecosystem.queries.timetable;

import java.util.Calendar;

public class TimetableQueries {

	public static String getRoutesInAdminAreaQuery(String adminAreaUri,
			boolean includeDirections) {
		return getLinesInAdminAreaQuery(
				QueryReader.getString("TimetableQueries.query.get.lines.route.type"),
				(includeDirections) ? String.format(
						QueryReader.getString("TimetableQueries.query.get.lines.direction"),
						QueryReader.getString("TimetableQueries.query.get.lines.route.property"))
						: "", adminAreaUri);
	}

	public static String getServicesInAdminAreaQuery(String adminAreaUri,
			boolean includeDirections) {
		return getLinesInAdminAreaQuery(
				QueryReader.getString("TimetableQueries.query.get.lines.service.type"),
				(includeDirections) ? String.format(
						QueryReader.getString("TimetableQueries.query.get.lines.direction"),
						QueryReader.getString("TimetableQueries.query.get.lines.service.property"))
						: "", adminAreaUri);
	}

	private static String getLinesInAdminAreaQuery(String lineType,
			String directionQuery, String adminAreaUri) {
		return String.format(
				QueryReader.getString("TimetableQueries.query.get.lines"),
				lineType, adminAreaUri, directionQuery);
	}

	public static String getRouteDirectionsQuery(String routeUri) {
		return getLineDirectionsQuery(
				QueryReader.getString("TimetableQueries.query.get.lines.route.property"),
				routeUri);
	}

	public static String getServiceDirectionsQuery(String serviceUri) {
		return getLineDirectionsQuery(
				QueryReader.getString("TimetableQueries.query.get.lines.service.property"),
				serviceUri);

	}

	private static String getLineDirectionsQuery(String lineLinkProperty,
			String lineUri) {
		return String.format(
				QueryReader.getString("TimetableQueries.query.get.direction"),
				lineLinkProperty, lineUri);
	}

	public static String getServicesOnRouteQuery(String routeUri) {
		return String.format(QueryReader
				.getString("TimetableQueries.query.get.servicesOnRoute"),
				routeUri);
	}

	public static String getBusLocationsOnRouteQuery(String routeUri,
			boolean inbound) {
		return getBusLocationsOnLine(
				QueryReader.getString("TimetableQueries.query.get.lines.route.property"),routeUri,
				inbound);
	}

	public static String getBusLocationsOnServiceQuery(String serviceUri,
			boolean inbound) {
		return getBusLocationsOnLine(
				QueryReader.getString("TimetableQueries.query.get.lines.servuce.property"),serviceUri,
				inbound);
	}

	private static String getBusLocationsOnLine(String lineLink, String lineUri, boolean inbound) {
		Calendar cal = Calendar.getInstance();
		String dayOfWeek = getDayOfWeekProperty(cal);
		String time = currentTimeString(cal).toString();
		String query = String
				.format(QueryReader
						.getString("TimetableQueries.query.get.busLocation"),
						lineLink,
						lineUri,
						((inbound) ? QueryReader
								.getString("TimetableQueries.query.get.busLocation.inbound")
								: QueryReader
										.getString("TimetableQueries.query.get.busLocation.outbound")),
						dayOfWeek, time, time);
		return query;
	}

	private static String getDayOfWeekProperty(Calendar cal) {
		switch (cal.get(Calendar.DAY_OF_WEEK)) {
		case Calendar.MONDAY:
			return QueryReader
					.getString("TimetableQueryProxy.query.get.busLocation.monday");
		case Calendar.TUESDAY:
			return QueryReader
					.getString("TimetableQueryProxy.query.get.busLocation.tuesday");
		case Calendar.WEDNESDAY:
			return QueryReader
					.getString("TimetableQueryProxy.query.get.busLocation.wednesday");
		case Calendar.THURSDAY:
			return QueryReader
					.getString("TimetableQueryProxy.query.get.busLocation.thursday");
		case Calendar.FRIDAY:
			return QueryReader
					.getString("TimetableQueryProxy.query.get.busLocation.friday");
		case Calendar.SATURDAY:
			return QueryReader
					.getString("TimetableQueryProxy.query.get.busLocation.saturday");
		case Calendar.SUNDAY:
			return QueryReader
					.getString("TimetableQueryProxy.query.get.busLocation.sunday");
		}
		return "[]";
	}

	private static StringBuilder currentTimeString(Calendar cal) {
		StringBuilder time = new StringBuilder("");
		if (cal.get(Calendar.HOUR_OF_DAY) < 10) {
			time.append("0");
		}
		time.append(cal.get(Calendar.HOUR_OF_DAY)).append(":");
		if (cal.get(Calendar.MINUTE) < 10) {
			time.append("0");
		}
		time.append(cal.get(Calendar.MINUTE)).append(":00");
		return time;
	}
}
