package uk.ac.dotrural.irp.ecosystem.models.jaxb.journey;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import uk.ac.dotrural.irp.ecosystem.models.jaxb.device.Device;
import uk.ac.dotrural.irp.ecosystem.models.jaxb.timetable.Line;
import uk.ac.dotrural.irp.ecosystem.models.jaxb.user.User;

@XmlRootElement
public class Journey
{
  private String uri;
  private User user;
  private Line line;
  private Device device;
  
  public Journey()
  {}
  
  public Journey(String uri)
  {
    this.uri = uri;
  }
  
  @XmlElement(name="uri")
  public String getUri()
  {
    return uri;
  }

  public void setUri(String uri)
  {
    this.uri = uri;
  }

  @XmlElement(name="user")
  public User getUser()
  {
    return user;
  }
  
  public void setUser(User user)
  {
    this.user = user;
  }
  
  @XmlElement(name="line")
  public Line getLine()
  {
    return line;
  }
  
  public void setLine(Line line)
  {
    this.line = line;
  }
  
  @XmlElement(name="device")
  public Device getDevice()
  {
    return device;
  }
  
  public void setDevice(Device device)
  {
    this.device = device;
  }
}
