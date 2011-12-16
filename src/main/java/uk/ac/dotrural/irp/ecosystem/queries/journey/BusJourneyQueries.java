package uk.ac.dotrural.irp.ecosystem.queries.journey;



/**
 * 
 * @author david corsar
 *
 */
public class BusJourneyQueries {
	public static String getCreateBusJourneyUpdate(String userUri, String deviceUri,
			String routeUri, String uri) {
		String updateQuery = String.format(
				QueryReader.getString("JourneyQueries.update.create"), uri,
				routeUri, deviceUri, userUri);
		return updateQuery;
	}
	public static String getBusJourneyQuery(String journeyUri) {
		return String.format(
				QueryReader.getString("JourneyQueries.query.get"), journeyUri);
	}
	public static String getBusJourneysByUserQuery(String userUri) {
		return String.format(
				QueryReader.getString("JourneyQueries.query.getByUser"),
				userUri);
	}
	public static String getBusJourneysOnRouteQuery(String routeUri) {
		return String.format(
				QueryReader.getString("JourneyQueries.query.getByRoute"),
				routeUri);
	}
	public static String getDeleteBusJourneyUpdate(String journeyUri) {
		return String.format(
				QueryReader.getString("JourneyQueries.update.delete"),
				journeyUri, journeyUri);
	}
}
