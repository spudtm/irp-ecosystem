package uk.ac.dotrural.irp.ecosystem.models.jaxb.journey;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import uk.ac.dotrural.irp.ecosystem.models.jaxb.device.Device;

@XmlRootElement
public class JourneyPayload
{
  private String authenticationToken;
  private JourneyType type;
  private String userUri;
  private Device device;
  
  @XmlElement(name="authenticationToken")
  public String getAuthenticationToken()
  {
    return authenticationToken;
  }
  
  public void setAuthenticationToken(String authenticationToken)
  {
    this.authenticationToken = authenticationToken;
  }
  
  @XmlElement(name="type")
  public JourneyType getType()
  {
    return type;
  }
  
  public void setType(JourneyType type)
  {
    this.type = type;
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
