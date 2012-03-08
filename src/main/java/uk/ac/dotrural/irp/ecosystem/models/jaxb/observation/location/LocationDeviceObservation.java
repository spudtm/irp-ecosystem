package uk.ac.dotrural.irp.ecosystem.models.jaxb.observation.location;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LocationDeviceObservation extends LocationObservation {

	
	private LocationDeviceValues locationValues;
	
	@XmlElement(name="locationValues")
	public LocationDeviceValues getLocationValues() {
		return locationValues;
	}

	public void setLocationValues(LocationDeviceValues locationValues) {
		this.locationValues = locationValues;
	}
	
}
