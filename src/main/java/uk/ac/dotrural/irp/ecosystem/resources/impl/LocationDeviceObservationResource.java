package uk.ac.dotrural.irp.ecosystem.resources.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.springframework.context.annotation.Scope;

import uk.ac.dotrural.irp.ecosystem.models.jaxb.observation.FeatureOfInterest;
import uk.ac.dotrural.irp.ecosystem.models.jaxb.observation.Observation;
import uk.ac.dotrural.irp.ecosystem.models.jaxb.observation.ObservationValue;
import uk.ac.dotrural.irp.ecosystem.models.jaxb.observation.Sensing;
import uk.ac.dotrural.irp.ecosystem.models.jaxb.observation.Sensor;
import uk.ac.dotrural.irp.ecosystem.models.jaxb.observation.SensorOutput;
import uk.ac.dotrural.irp.ecosystem.models.jaxb.observation.location.LocationDeviceObservationValue;
import uk.ac.dotrural.irp.ecosystem.models.jaxb.observation.location.LocationDeviceObservation;
import uk.ac.dotrural.irp.ecosystem.models.jaxb.observation.location.LocationDeviceObservationPayload;
import uk.ac.dotrural.irp.ecosystem.models.jaxb.observation.location.LocationDeviceValues;
import uk.ac.dotrural.irp.ecosystem.models.jaxb.system.EndpointInfo;
import uk.ac.dotrural.irp.ecosystem.models.jaxb.system.Query;
import uk.ac.dotrural.irp.ecosystem.models.jaxb.system.ServiceInitialiser;
import uk.ac.dotrural.irp.ecosystem.models.jaxb.system.SystemMessage;
import uk.ac.dotrural.irp.ecosystem.queries.observation.ObservationQueries;
import uk.ac.dotrural.irp.ecosystem.queries.observation.QueryReader;
import uk.ac.dotrural.irp.ecosystem.queries.user.UserQueries;
import uk.ac.dotrural.irp.ecosystem.resources.RESTFulSPARQL;
import uk.ac.dotrural.irp.ecosystem.resources.support.reporters.ExceptionReporter;
import uk.ac.dotrural.irp.ecosystem.services.SPARQLEndpoint;
import uk.ac.dotrural.irp.ecosystem.util.Util;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

@Path("/observation")
@Scope("request")
public class LocationDeviceObservationResource implements RESTFulSPARQL {
	@Context
	private UriInfo uriInfo;

	private SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss z");

	private SPARQLEndpoint observationEndpoint;

	public void setObservationEndpoint(SPARQLEndpoint observationsEndpoint) {
		this.observationEndpoint = observationsEndpoint;
	}

	public SystemMessage init(ServiceInitialiser si) {
		return observationEndpoint.init(uriInfo, si);
	}

	public void update(Query query) {
		observationEndpoint.update(query);
	}

	public String query(Query query) {
		return Util.resultsetToString(observationEndpoint.query(query));
	}

	public EndpointInfo info() {
		return observationEndpoint.info();
	}

	public static void main(String[] args) throws ParseException {
		LocationDeviceObservationResource d = new LocationDeviceObservationResource();
		d.dateFormat.parse("2012-02-13 12:01:01 +0000");
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("create")
	public LocationDeviceObservation create(
			LocationDeviceObservationPayload locationObservationPayload) {
		if (locationObservationPayload == null) {
			throw new ExceptionReporter(new NullPointerException(
					"No LocationObservationPayload given"));
		}

		if (locationObservationPayload.getUserUri() == null
				|| "".equals(locationObservationPayload.getUserUri().trim())) {
			throw new ExceptionReporter(new NullPointerException(
					"User uri required to create the location observation"));
		}

		if (locationObservationPayload.getType() == null
				|| "".equals(locationObservationPayload.getType().trim())) {
			throw new ExceptionReporter(new NullPointerException(
					"Type required to create the location observation"));
		}

		if (locationObservationPayload.getSensor() == null
				|| "".equals(locationObservationPayload.getSensor().trim())) {
			throw new ExceptionReporter(new NullPointerException(
					"Sensor required to create the location observation"));
		}

		if (locationObservationPayload.getValues() == null) {
			throw new ExceptionReporter(
					new NullPointerException(
							"Location values required to create the location observation"));
		}
		if (ObservationQueries.getLocationDeviceObservationType().equals(
				locationObservationPayload.getType().trim())) {
			// create the location device observation
			LocationDeviceValues values = locationObservationPayload
					.getValues();
			if (values.getLatitude() == null) {
				throw new ExceptionReporter(new NullPointerException(
						"Latitude required to create the location observation"));
			}
			if (values.getLongitude() == null) {
				throw new ExceptionReporter(
						new NullPointerException(
								"Longitude required to create the location observation"));
			}
			System.out.println(values.getDeviceTime().trim());
			if (values.getDeviceTime() == null
					|| "".equals(values.getDeviceTime().trim())) {
				throw new ExceptionReporter(
						new NullPointerException(
								"Device time required to create the location observation"));
			}
			try {
				dateFormat.parse(values.getDeviceTime().trim());
			} catch (ParseException e) {
				throw new ExceptionReporter(
						new IllegalArgumentException(
								"Device time must be of the form yyyy-mm-dd hh:mm:ss z"));
			}

			if (values.getGpsTime() == null
					|| "".equals(values.getGpsTime().trim())) {
				throw new ExceptionReporter(
						new NullPointerException(
								"GPS timestamp required to create the location observation"));
			}

			try {
				dateFormat.parse(values.getGpsTime().trim());
			} catch (ParseException e) {
				throw new ExceptionReporter(
						new IllegalArgumentException(
								"GPS timestamp must be of the form yyyy-mm-dd hh:mm:ss z"));
			}

			// bulid query
			String observationUri = QueryReader
					.getString("ObservationQueries.baseNs") + UUID.randomUUID();
			String outputUri = QueryReader
					.getString("ObservationQueries.baseNs") + UUID.randomUUID();
			String valueUri = QueryReader
					.getString("ObservationQueries.baseNs") + UUID.randomUUID();
			String serverTime = Util.timeNow();
			String deviceSensorUri = QueryReader
					.getString("ObservationQueries.baseNs") + UUID.randomUUID();
			String sensingMethodUsedUri = (locationObservationPayload
					.getSensingMethodUsed() == null || ""
					.equals(locationObservationPayload.getSensingMethodUsed())) ? null
					: locationObservationPayload.getSensingMethodUsed();

			String queryStr = ObservationQueries
					.createLocationDeviceObservationUpdate(
							locationObservationPayload.getFeatureOfInterest()
									.trim(),
							values.getLatitude().doubleValue(), values
									.getLongitude().doubleValue(), values
									.getGpsTime(), values.getDeviceTime(),
							values.getAccuracy(), values.getHeading(), values
									.getSpeed(), observationUri, outputUri,
							valueUri, serverTime, deviceSensorUri,
							sensingMethodUsedUri);

			// perform update
			Query query = new Query(queryStr);
			observationEndpoint.update(query);

			// build result object
			LocationDeviceObservation observation = new LocationDeviceObservation();
			observation.setUri(observationUri);

			return observation;
		}

		throw new ExceptionReporter(new IllegalArgumentException(
				"Unrecognised observation type"));
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("get")
	public Observation get(
			@DefaultValue("") @QueryParam("observationUri") String observationUri) {
		observationUri = observationUri.trim();
		if ("".equals(observationUri)) {
			throw new ExceptionReporter(new IllegalArgumentException(
					"observationUri is null or empty"));
		}

		String query = ObservationQueries
				.getLocationDeviceObservationQuery(observationUri);
		ResultSet results = observationEndpoint.query(new Query(query));
		Observation obs = new Observation();
		obs.setUri(observationUri);
		List<String> vars = results.getResultVars();
		// ?observationResult ?foi ?observedBy ?sensingMethodUsed
		// ?osbservationResultTime ?observationSamplingTime ?serverTime
		if (results.hasNext()) {
			QuerySolution solution = results.next();
			obs.setObservationResult(new SensorOutput(Util.getNodeValue(
					solution.get(vars.get(0))).trim()));
			obs.setFeatureOfInterest(new FeatureOfInterest(Util.getNodeValue(
					solution.get(vars.get(1))).trim()));
			obs.setObservedBy(new Sensor(Util.getNodeValue(
					solution.get(vars.get(2))).trim()));
			String method = Util.getNodeValue(solution.get(vars.get(3))).trim();
			if (null != method && !("".equals(method))) {
				obs.setSensingMethodUsed(new Sensing(method));
			}
			obs.setObservationResultTime(Util.getNodeValue(
					solution.get(vars.get(4))).trim());
			obs.setObservationSamplingTime(Util.getNodeValue(
					solution.get(vars.get(5))).trim());
			obs.setServerTime(Util.getNodeValue(solution.get(vars.get(6)))
					.trim());
		}
		return obs;
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("getSensorOutput")
	public SensorOutput getSensorOutput(
			@DefaultValue("") @QueryParam("sensorOutputUri") String sensorOutputUri) {
		sensorOutputUri = sensorOutputUri.trim();
		if ("".equals(sensorOutputUri)) {
			throw new ExceptionReporter(new IllegalArgumentException(
					"observationUri is null or empty"));
		}

		// setup and execute the query
		String query = ObservationQueries
				.getLocationDeviceSensorOutputQuery(sensorOutputUri);
		ResultSet results = observationEndpoint.query(new Query(query));

		List<String> resultVars = results.getResultVars();
		
		// process the results
		SensorOutput so = new SensorOutput();
		if (results.hasNext()) {
			QuerySolution qs = results.next();
			so.setUri(sensorOutputUri);
			ObservationValue value = new ObservationValue();
			value.setUri(Util.getNodeValue(qs.get(resultVars.get(0))));
			so.setHasValue(value);
		}
		return so;
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("getObservationValue")
	public ObservationValue getObservationValue(
			@DefaultValue("") @QueryParam("observationValueUri") String observationValueUri) {
		observationValueUri = observationValueUri.trim();
		if ("".equals(observationValueUri)) {
			throw new ExceptionReporter(new IllegalArgumentException(
					"observationUri is null or empty"));
		}

		// setup and execute the query
		String query = ObservationQueries
				.getLocationDeviceObservationValueQuery(observationValueUri);
		return extractLocationDeviceObservationValue(observationValueUri, query);
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("getValueForObservation")
	public ObservationValue getValueForObservation(
			@DefaultValue("") @QueryParam("observationUri") String observationUri) {
		observationUri = observationUri.trim();
		if ("".equals(observationUri)) {
			throw new ExceptionReporter(new IllegalArgumentException(
					"observationUri is null or empty"));
		}

		// setup and execute the query
		String query = ObservationQueries
				.getLocationDeviceObservationValueForObservationQuery(observationUri);
		return extractLocationDeviceObservationValue(observationUri, query);
	}

	private ObservationValue extractLocationDeviceObservationValue(
			String uri, String query) {
		ResultSet results = observationEndpoint.query(new Query(query));

		List<String> resultVars = results.getResultVars();
		// process the results
		LocationDeviceObservationValue value = new LocationDeviceObservationValue();
		if (results.hasNext()) {
			QuerySolution qs = results.next();
			value.setLatitude(Util.getNodeDoubleValue(qs.get(resultVars
					.get(0))));
			value.setLongitude(Util.getNodeDoubleValue(qs
					.get(resultVars.get(1))));
			value.setEasting(Util.getNodeDoubleValue(qs.get(resultVars
					.get(2))));
			value.setNorthing(Util.getNodeDoubleValue(qs.get(resultVars
					.get(3))));
			value.setAccuracy(Util.getNodeDoubleValue(qs.get(resultVars
					.get(4))));
			value.setHeading(Util.getNodeDoubleValue(qs.get(resultVars
					.get(5))));
			value.setSpeed(Util.getNodeDoubleValue(qs.get(resultVars
					.get(6))));
			value.setUri((resultVars.size() == 7)?Util.getNodeValue(qs.get(resultVars.get(7))):uri);
		}
		return value;
	}

	@DELETE
	@Consumes({ MediaType.APPLICATION_JSON })
	@Path("delete")
	public void delete(
			@DefaultValue("") @QueryParam("observationUri") String observationUri,
			@DefaultValue("") @QueryParam("authenticationToken") String authenticationToken) {
		observationUri = observationUri.trim();
		authenticationToken = authenticationToken.trim();

		if (observationUri.equals("") && authenticationToken.equals(""))
			throw new ExceptionReporter(
					new NullPointerException(
							"'observationUri' or 'authenticationToken' is needed to delete the user."));

		String accuracyQuery = ObservationQueries.deleteLocationDeviceObservationAccuracy(observationUri);
		String headingQuery = ObservationQueries.deleteLocationDeviceObservationHeading(observationUri);
		String speedQuery = ObservationQueries.deleteLocationDeviceObservationSpeed(observationUri);
		String methodQuery = ObservationQueries.deleteLocationDeviceObservationSensingMethodUsed(observationUri);
		String obsQuery = ObservationQueries.deleteLocationDeviceObservation(observationUri);
		
		observationEndpoint.update(new Query(accuracyQuery),new Query(headingQuery), new Query(speedQuery),new Query(methodQuery), new Query(obsQuery) );
	}

}
