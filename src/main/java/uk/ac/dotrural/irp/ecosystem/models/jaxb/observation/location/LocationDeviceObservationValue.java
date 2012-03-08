package uk.ac.dotrural.irp.ecosystem.models.jaxb.observation.location;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LocationDeviceObservationValue extends LocationObservationValue {
	private Double accuracy, heading, speed;

	public LocationDeviceObservationValue() {
		super();
	}

	@XmlElement(name = "accuracy")
	public Double getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(Double accuracy) {
		this.accuracy = accuracy;
	}

	@XmlElement(name = "heading")
	public Double getHeading() {
		return heading;
	}

	public void setHeading(Double heading) {
		this.heading = heading;
	}

	@XmlElement(name = "speed")
	public Double getSpeed() {
		return speed;
	}

	public void setSpeed(Double speed) {
		this.speed = speed;
	}

}
