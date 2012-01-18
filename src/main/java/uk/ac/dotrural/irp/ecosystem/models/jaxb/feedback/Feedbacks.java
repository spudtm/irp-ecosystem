package uk.ac.dotrural.irp.ecosystem.models.jaxb.feedback;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Feedbacks
{
  private List<Feedback> feedbacks;

  public Feedbacks()
  {}
  
  public Feedbacks(List<Feedback> feedbacks)
  {
    this.feedbacks = feedbacks;
  }
  
  @XmlElement(name="feedbacks")
  public List<Feedback> getFeedbacks()
  {
    return feedbacks;
  }

  public void setFeedbacks(List<Feedback> feedbacks)
  {
    this.feedbacks = feedbacks;
  }
}
