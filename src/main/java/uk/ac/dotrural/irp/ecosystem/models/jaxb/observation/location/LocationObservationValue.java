package uk.ac.dotrural.irp.ecosystem.models.jaxb.observation.location;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import uk.ac.dotrural.irp.ecosystem.models.jaxb.observation.ObservationValue;


@XmlRootElement
public class LocationObservationValue extends ObservationValue{
	
	private Double longitude, latitude, easting, northing;

	public LocationObservationValue(){
		super();
	}
	@XmlElement(name = "longitude")
	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	@XmlElement(name = "latitude")
	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	@XmlElement(name = "easting")
	public Double getEasting() {
		return easting;
	}

	public void setEasting(Double easting) {
		this.easting = easting;
	}
	@XmlElement(name = "northing")
	public Double getNorthing() {
		return northing;
	}

	public void setNorthing(Double northing) {
		this.northing = northing;
	}
	
	

}
