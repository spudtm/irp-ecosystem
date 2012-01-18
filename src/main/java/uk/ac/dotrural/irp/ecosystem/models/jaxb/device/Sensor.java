package uk.ac.dotrural.irp.ecosystem.models.jaxb.device;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Sensor
{
  private String uri;
  private String observes;
  private String sensingMethod;
  
  @XmlElement(name="uri")
  public String getUri()
  {
    return uri;
  }
  
  public void setUri(String uri)
  {
    this.uri = uri;
  }
  
  @XmlElement(name="observes")
  public String getObserves()
  {
    return observes;
  }
  
  public void setObserves(String observes)
  {
    this.observes = observes;
  }
  
  @XmlElement(name="sensingMethod")
  public String getSensingMethod()
  {
    return sensingMethod;
  }
  
  public void setSensingMethod(String sensingMethod)
  {
    this.sensingMethod = sensingMethod;
  }
}
