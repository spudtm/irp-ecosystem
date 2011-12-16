package uk.ac.dotrural.irp.ecosystem.models.jaxb.transport;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BusStop
{
  private String uri;
  private String prefLabel;
  private String street;
  
  @XmlElement(name="uri")
  public String getUri()
  {
    return uri;
  }
  
  public void setUri(String uri)
  {
    this.uri = uri;
  }
  
  @XmlElement(name="prefLabel")
  public String getPrefLabel()
  {
    return prefLabel;
  }
  
  public void setPrefLabel(String prefLabel)
  {
    this.prefLabel = prefLabel;
  }
  
  @XmlElement(name="street")
  public String getStreet()
  {
    return street;
  }
  
  public void setStreet(String street)
  {
    this.street = street;
  }
}
