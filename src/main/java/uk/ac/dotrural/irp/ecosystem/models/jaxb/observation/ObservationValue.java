package uk.ac.dotrural.irp.ecosystem.models.jaxb.observation;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ObservationValue {

	private String uri;

	public ObservationValue() {
		super();
	}

	@XmlElement(name = "uri")
	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
	
}
