package uk.ac.dotrural.irp.ecosystem.queries.alert;


import java.text.DecimalFormat;

public class BusAlertQueries {
	private static DecimalFormat format = new DecimalFormat("00");

	public static String getCreateAlertUpdate(String newAlertUri, String userUri, String routeUri,
			boolean inbound, double latitude, double longitude,
			int startTimeHour, int startTimeMin, int endTimeHour,
			int endTimeMin, int minutesBefore, 
			ServiceDays... daysOfWeek) {
		return String
				.format(QueryReader.getString("BusAlertQueries.update.create"), //$NON-NLS-1$
						newAlertUri,
						userUri,
						routeUri,
						((inbound) ? QueryReader
								.getString("BusAlertQueries.inboundString") : QueryReader.getString("BusAlertQueries.outboundString")), //$NON-NLS-1$ //$NON-NLS-2$
						makeDaysString(daysOfWeek),
						makeTimeAsXsdTime(startTimeHour, startTimeMin,0),
						makeTimeAsXsdTime(endTimeHour, endTimeMin,0),
						minutesBefore, toXsdDouble(latitude),
						toXsdDouble(longitude));
	}
	
	public static String getDeleteAlertUpdate(String alertUri) {
		return String.format(
				QueryReader.getString("BusAlertQueries.update.delete"), //$NON-NLS-1$
				alertUri, alertUri, alertUri, alertUri, alertUri, alertUri,
				alertUri, alertUri, alertUri, alertUri);
	}
	

	public static String getAlertsForUserQuery(String userUri) {
		return String.format(QueryReader
				.getString("BusAlertQueries.query.getForUser"),
				userUri);
	}

	public static String getAlertQuery(String alertUri) {
		return String.format(
				QueryReader.getString("BusAlertQueries.query.get"), alertUri,
				alertUri, alertUri, alertUri, alertUri, alertUri, alertUri,
				alertUri, alertUri);
	}
	
	private static String makeDaysString(ServiceDays[] array) {
		StringBuilder sb = new StringBuilder();
		for (ServiceDays day : array) {
			if (ServiceDays.MONDAY == day) {
				sb.append(QueryReader.getString("BusAlertQueries.mondayTrue")); //$NON-NLS-1$
			} else if (ServiceDays.TUESDAY == day) {
				sb.append(QueryReader.getString("BusAlertQueries.tuesdayTrue")); //$NON-NLS-1$
			} else if (ServiceDays.WEDNESDAY == day) {
				sb.append(QueryReader
						.getString("BusAlertQueries.wednesdayTrue")); //$NON-NLS-1$
			} else if (ServiceDays.THURSDAY == day) {
				sb.append(QueryReader.getString("BusAlertQueries.thursdayTrue")); //$NON-NLS-1$
			} else if (ServiceDays.FRIDAY == day) {
				sb.append(QueryReader.getString("BusAlertQueries.fridayTrue")); //$NON-NLS-1$
			} else if (ServiceDays.SATURDAY == day) {
				sb.append(QueryReader.getString("BusAlertQueries.saturdayTrue")); //$NON-NLS-1$
			} else if (ServiceDays.SUNDAY == day) {
				sb.append(QueryReader.getString("BusAlertQueries.sundayTrue")); //$NON-NLS-1$
			} else if (ServiceDays.BANK_HOLIDAYS == day) {
				sb.append(QueryReader
						.getString("BusAlertQueries.bankholidaysTrue")); //$NON-NLS-1$
			}
		}
		return sb.toString();
	}

	public static String toXsdDouble(double d) {
		StringBuilder sb = new StringBuilder("\"");
		sb.append(d).append("\"^^xsd:double");
		return sb.toString();
	}

	public static String makeTimeAsXsdTime(int hour, int min, int seconds) {
		StringBuilder sb = new StringBuilder("\"");
		sb.append(makeTime(hour, min, seconds)).append("\"^^xsd:time");
		return sb.toString();
	}

	public static String makeTime(int hour, int min, int seconds) {
		StringBuilder sb = new StringBuilder(format.format(hour));
		sb.append(":").append(format.format(min)).append(":")
				.append(format.format(seconds));
		return sb.toString();
	}
}
