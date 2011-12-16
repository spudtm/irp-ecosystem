package uk.ac.dotrural.irp.ecosystem.models.jaxb.timetable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Location
{
  private String time;
  private int easting;
  private int westing;
  private int longitude;
  private int latitude;
  
  @XmlElement(name="time")
  public String getTime()
  {
    return time;
  }
  
  public void setTime(String time)
  {
    this.time = time;
  }
  
  @XmlElement(name="easting")
  public int getEasting()
  {
    return easting;
  }
  
  public void setEasting(int easting)
  {
    this.easting = easting;
  }
  
  @XmlElement(name="westing")
  public int getWesting()
  {
    return westing;
  }
  
  public void setWesting(int westing)
  {
    this.westing = westing;
  }
  
  @XmlElement(name="longitude")
  public int getLongitude()
  {
    return longitude;
  }
  
  public void setLongitude(int longitude)
  {
    this.longitude = longitude;
  }
  
  @XmlElement(name="latitude")
  public int getLatitude()
  {
    return latitude;
  }
  
  public void setLatitude(int latitude)
  {
    this.latitude = latitude;
  }
}
