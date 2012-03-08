package uk.ac.dotrural.irp.ecosystem.models.jaxb.observation;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FeatureOfInterest {

	private String uri;


	public FeatureOfInterest(){
		super();
	}
	
	public FeatureOfInterest(String uri) {
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
