package uk.ac.dotrural.irp.ecosystem.models.jaxb.feedback;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import uk.ac.dotrural.irp.ecosystem.models.jaxb.journey.Journey;
import uk.ac.dotrural.irp.ecosystem.models.jaxb.user.User;

@XmlRootElement
public class Feedback
{
  private String uri;
  private Journey journey;
  private User user;
  private String message;
  
  @XmlElement(name="uri")
  public String getUri()
  {
    return uri;
  }
  
  public void setUri(String uri)
  {
    this.uri = uri;
  }
  
  @XmlElement(name="journey")
  public Journey getJourney()
  {
    return journey;
  }
  
  public void setJourney(Journey journey)
  {
    this.journey = journey;
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
  
  @XmlElement(name="message")
  public String getMessage()
  {
    return message;
  }
  
  public void setMessage(String message)
  {
    this.message = message;
  }
}
