package uk.ac.dotrural.irp.ecosystem.models.jaxb.feedback;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FeedbackPayload
{
  private String userUri;
  private String feedbackUri;
  private String journeyUri;
  private String message;
  private String authenticationToken;
  
  @XmlElement(name="userUri")
  public String getUserUri()
  {
    return userUri;
  }
  
  public void setUserUri(String userUri)
  {
    this.userUri = userUri;
  }
  
  @XmlElement(name="feedbackUri")
  public String getFeedbackUri()
  {
    return feedbackUri;
  }
  
  public void setFeedbackUri(String feedbackUri)
  {
    this.feedbackUri = feedbackUri;
  }
  
  @XmlElement(name="journeyUri")
  public String getJourneyUri()
  {
    return journeyUri;
  }
  
  public void setJourneyUri(String journeyUri)
  {
    this.journeyUri = journeyUri;
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
  
  @XmlElement(name="authenticationToken")
  public String getAuthenticationToken()
  {
    return authenticationToken;
  }
  
  public void setAuthenticationToken(String authenticationToken)
  {
    this.authenticationToken = authenticationToken;
  }
}
