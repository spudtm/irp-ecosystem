package uk.ac.dotrural.irp.ecosystem.models.jaxb.observation.location;

import javax.xml.bind.annotation.XmlElement;

public class LocationDeviceValues {

	private String  gpsTime, deviceTime;
	private Integer accuracy;
	private Double latitude, longitude, heading, speed;
	
	@XmlElement(name="gpsTime")
	public String getGpsTime() {
		return this.gpsTime;
	}
	public void setGpsTime(String gpsTime) {
		this.gpsTime = gpsTime;
	}
	@XmlElement(name="deviceTime")
	public String getDeviceTime() {
		return deviceTime;
	}
	public void setDeviceTime(String deviceTime) {
		this.deviceTime = deviceTime;
	}
	@XmlElement(name="accuracy")
	public Integer getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(Integer accuracy) {
		this.accuracy = accuracy;
	}
	@XmlElement(name="latitude")
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	@XmlElement(name="longitude")
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	@XmlElement(name="heading")
	public Double getHeading() {
		return heading;
	}
	public void setHeading(Double heading) {
		this.heading = heading;
	}
	@XmlElement(name="speed")
	public Double getSpeed() {
		return speed;
	}
	public void setSpeed(Double speed) {
		this.speed = speed;
	}
	
	
	
}
