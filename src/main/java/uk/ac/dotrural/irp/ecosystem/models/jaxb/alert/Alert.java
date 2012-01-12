package uk.ac.dotrural.irp.ecosystem.models.jaxb.alert;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Alert
{
  private String uri;
  private String authenticationToken;
  private String userUri;
  private String lineUri;
  private boolean inbound;
  private double latitude;
  private double longitude;
  private int startTimeHour;
  private int startTimeMin;
  private int endTimeHour;
  private int endTimeMin;
  private int minutesBefore;
  private boolean [] days;
  
  @XmlElement(name="uri")
  public String getUri()
  {
    return uri;
  }
  
  public void setUri(String uri)
  {
    this.uri = uri;
  }
  
  @XmlElement(name="authenticationToken")
  public String getAuthenticationToken()
  {
    return authenticationToken;
  }
  
  public void setAuthenticationToken(String authenticationToken)
  {
    this.authenticationToken = authenticationToken;
  }
  
  @XmlElement(name="userUri")
  public String getUserUri()
  {
    return userUri;
  }
  
  public void setUserUri(String userUri)
  {
    this.userUri = userUri;
  }
  
  @XmlElement(name="lineUri")
  public String getLineUri()
  {
    return lineUri;
  }
  
  public void setLineUri(String lineUri)
  {
    this.lineUri = lineUri;
  }
  
  @XmlElement(name="inbound")
  public boolean getInbound()
  {
    return inbound;
  }
  
  public void setInbound(boolean inbound)
  {
    this.inbound = inbound;
  }
  
  @XmlElement(name="latitude")
  public double getLatitude()
  {
    return latitude;
  }
  
  public void setLatitude(double latitude)
  {
    this.latitude = latitude;
  }
  
  @XmlElement(name="longitude")
  public double getLongitude()
  {
    return longitude;
  }
  
  public void setLongitude(double longitude)
  {
    this.longitude = longitude;
  }
  
  @XmlElement(name="startTimeHour")
  public int getStartTimeHour()
  {
    return startTimeHour;
  }
  
  public void setStartTimeHour(int startTimeHour)
  {
    this.startTimeHour = startTimeHour;
  }
  
  @XmlElement(name="startTimeMin")
  public int getStartTimeMin()
  {
    return startTimeMin;
  }
  
  public void setStartTimeMin(int startTimeMin)
  {
    this.startTimeMin = startTimeMin;
  }
  
  @XmlElement(name="endTimeHour")
  public int getEndTimeHour()
  {
    return endTimeHour;
  }
  
  public void setEndTimeHour(int endTimeHour)
  {
    this.endTimeHour = endTimeHour;
  }
  
  @XmlElement(name="endTimeMin")
  public int getEndTimeMin()
  {
    return endTimeMin;
  }
  
  public void setEndTimeMin(int endTimeMin)
  {
    this.endTimeMin = endTimeMin;
  }
  
  @XmlElement(name="minutesBefore")
  public int getMinutesBefore()
  {
    return minutesBefore;
  }
  
  public void setMinutesBefore(int minutesBefore)
  {
    this.minutesBefore = minutesBefore;
  }
  
  @XmlElement(name="days")
  public boolean[] getDays()
  {
    return days;
  }
  
  public void setDays(boolean[] days)
  {
    this.days = days;
  }
}
