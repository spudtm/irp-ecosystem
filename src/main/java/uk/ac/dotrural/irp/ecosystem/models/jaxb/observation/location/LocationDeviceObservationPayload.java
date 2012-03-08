/**
 * 
 */
package uk.ac.dotrural.irp.ecosystem.models.jaxb.observation.location;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author david
 *
 */
@XmlRootElement
public class LocationDeviceObservationPayload extends LocationObservationPayload {
	private LocationDeviceValues values;
	
	@XmlElement(name="values")
	public LocationDeviceValues getValues() {
		return values;
	}

	public void setValues(LocationDeviceValues values) {
		this.values = values;
	}
}
