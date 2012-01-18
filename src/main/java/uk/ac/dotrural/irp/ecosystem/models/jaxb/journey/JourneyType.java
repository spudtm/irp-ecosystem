package uk.ac.dotrural.irp.ecosystem.models.jaxb.journey;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class JourneyType
{
  private String uri;
  private String routeUri;
  private String serviceUri;
  private String direction;
  
  @XmlElement(name="uri")
  public String getUri()
  {
    return uri;
  }
  
  public void setUri(String uri)
  {
    this.uri = uri;
  }
  
  @XmlElement(name="routeUri")
  public String getRouteUri()
  {
    return routeUri;
  }
  
  public void setRouteUri(String routeUri)
  {
    this.routeUri = routeUri;
  }
  
  @XmlElement(name="serviceUri")
  public String getServiceUri()
  {
    return serviceUri;
  }
  
  public void setServiceUri(String service)
  {
    this.serviceUri = service;
  }
  
  @XmlElement(name="direction")
  public String getDirection()
  {
    return direction;
  }
  
  public void setDirection(String direction)
  {
    this.direction = direction;
  }
}
