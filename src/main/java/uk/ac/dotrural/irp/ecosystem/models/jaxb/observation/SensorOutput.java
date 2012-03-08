/**
 * 
 */
package uk.ac.dotrural.irp.ecosystem.models.jaxb.observation;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author david
 * 
 */
@XmlRootElement
public class SensorOutput {

	private String uri;

	private ObservationValue hasValue;

	@XmlElement(name = "hasValue")
	public ObservationValue getHasValue() {
		return hasValue;
	}

	public void setHasValue(ObservationValue hasValue) {
		this.hasValue = hasValue;
	}

	public SensorOutput(){
		super();
	}
	
	public SensorOutput(String uri) {
		super();
		setUri(uri);
	}

	@XmlElement(name = "uri")
	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

}
