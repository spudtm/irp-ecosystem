package uk.ac.dotrural.irp.ecosystem.models.jaxb.observation;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Observation {

	private String uri, observationResultTime, observationSamplingTime,
			serverTime;
	private SensorOutput observationResult;
	private FeatureOfInterest featureOfInterest;
	private Sensor observedBy;
	private Sensing sensingMethodUsed;

	public Observation() {
		super();
	}

	public Observation(String observationUri) {
		super();
		setUri(observationUri);
	}

	@XmlElement(name = "uri")
	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	@XmlElement(name = "observationResult")
	public SensorOutput getObservationResult() {
		return observationResult;
	}

	public void setObservationResult(SensorOutput observationResult) {
		this.observationResult = observationResult;
	}

	@XmlElement(name = "featureOfInterest")
	public FeatureOfInterest getFeatureOfInterest() {
		return featureOfInterest;
	}

	public void setFeatureOfInterest(FeatureOfInterest featureOfInterest) {
		this.featureOfInterest = featureOfInterest;
	}

	@XmlElement(name = "observedBy")
	public Sensor getObservedBy() {
		return observedBy;
	}

	public void setObservedBy(Sensor observedBy) {
		this.observedBy = observedBy;
	}

	@XmlElement(name = "sensingMethodUsed")
	public Sensing getSensingMethodUsed() {
		return sensingMethodUsed;
	}

	public void setSensingMethodUsed(Sensing sensingMethodUsed) {
		this.sensingMethodUsed = sensingMethodUsed;
	}

	@XmlElement(name = "observationResultTime")
	public String getObservationResultTime() {
		return observationResultTime;
	}

	public void setObservationResultTime(String observationResultTime) {
		this.observationResultTime = observationResultTime;
	}

	@XmlElement(name = "observationSamplingTime")
	public String getObservationSamplingTime() {
		return observationSamplingTime;
	}

	public void setObservationSamplingTime(String observationSamplingTime) {
		this.observationSamplingTime = observationSamplingTime;
	}

	@XmlElement(name = "serverTime")
	public String getServerTime() {
		return serverTime;
	}

	public void setServerTime(String serverTime) {
		this.serverTime = serverTime;
	}

}
