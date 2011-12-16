/**
 * 
 */
package uk.ac.dotrural.irp.ecosystem.queries.transport;


/**
 * @author david corsar
 *
 */
public class NaptanNptgQueries {
	public static String getNptgRegionsQuery(){
		return QueryReader.getString("NaptanNptgQueries.query.get.nptgRegions");
	}
	public static String getNptgAdminAreasQuery(String regionUri) {
		return String.format(QueryReader.getString("NaptanNptgQueries.query.get.nptgAdminAreas"), regionUri);
	}

	public static  String getNptgLocalitiesQuery(String adminAreaUri) {
		return String.format(QueryReader.getString("NaptanNptgQueries.query.get.nptgLocalities"), adminAreaUri);
	}

	public static  String getBusStopPointsInLocalityQuery(String localityUri) {
		return String.format(QueryReader.getString("NaptanNptgQueries.query.get.busStopPointsInLocality"), localityUri);
	}
}
