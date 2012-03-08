package uk.ac.dotrural.irp.ecosystem.models.jaxb.observation;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ObservationPayload {

	private String userUri, authenticationToken, type, sensor,
			sensingMethodUsed, sensingController, featureOfInterest;

	@XmlElement(name = "userUri")
	public String getUserUri() {
		return userUri;
	}

	public void setUserUri(String userUri) {
		this.userUri = userUri;
	}

	@XmlElement(name = "authenticationToken")
	public String getAuthenticationToken() {
		return authenticationToken;
	}

	public void setAuthenticationToken(String authenticationToken) {
		this.authenticationToken = authenticationToken;
	}

	@XmlElement(name = "type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@XmlElement(name = "sensor")
	public String getSensor() {
		return sensor;
	}

	public void setSensor(String sensor) {
		this.sensor = sensor;
	}

	@XmlElement(name = "sensingMethodUsed")
	public String getSensingMethodUsed() {
		return sensingMethodUsed;
	}

	public void setSensingMethodUsed(String sensingMethodUsed) {
		this.sensingMethodUsed = sensingMethodUsed;
	}

	@XmlElement(name = "sensingController")
	public String getSensingController() {
		return sensingController;
	}

	public void setSensingController(String sensingController) {
		this.sensingController = sensingController;
	}

	@XmlElement(name = "featureOfInterest")
	public String getFeatureOfInterest() {
		return featureOfInterest;
	}

	public void setFeatureOfInterest(String featureOfInterest) {
		this.featureOfInterest = featureOfInterest;
	}

}
