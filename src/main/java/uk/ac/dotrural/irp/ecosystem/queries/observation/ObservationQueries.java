package uk.ac.dotrural.irp.ecosystem.queries.observation;


public class ObservationQueries {
	public static String getCreateLocationDeviceObservationUpdate(String journeyUri,
			double latitude, double longitude, String gpsTime,
			String deviceTime, int accuracy, double heading, double speed,
			String observationUri, String outputUri, String valueUri,
			String serverTime, String deviceSensorUri,
			String deviceLocationSensingUri) {
		String updateQuery = String.format(
				QueryReader.getString("ObservationQueries.update.create"),
				valueUri, latitude, longitude, gpsTime, deviceTime, serverTime,
				accuracy, heading, speed, outputUri, valueUri, observationUri,
				journeyUri, deviceSensorUri, deviceLocationSensingUri,
				outputUri);
		return updateQuery;
	}
	
	public static String getLocationDeviceObservationQuery(String observationUri) {
		return String
				.format(QueryReader
						.getString("ObservationQueries.query.get.locationDeviceObservation"),
						observationUri);
	}
	public static String getLocationDeviceSensorOutputQuery(String sensorOutputUri) {
		return String
				.format(QueryReader
						.getString("ObservationQueries.query.get.locationDeviceSensorOutput"),
						sensorOutputUri);
	}
	public static String getLocationDeviceValueForObservationQuery(
			String observationUri) {
		return String
				.format(QueryReader
						.getString("ObservationQueries.query.get.locationDeviceOutputValueForObservation"),
						observationUri);
	}
	public static String deleteLocationObservationUpdate(String observationUri) {
		return String.format(
				QueryReader.getString("ObservationQueries.update.delete"),
				observationUri, observationUri);
	}
	public static String getLocationDeviceValueQuery(String locationValurUri) {
		return String
				.format(QueryReader
						.getString("ObservationQueries.query.get.locationDeviceOutputValue"),
						locationValurUri);
	}
}
