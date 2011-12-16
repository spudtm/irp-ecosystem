package uk.ac.dotrural.irp.ecosystem.models.jaxb.transport;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Region
{
  private String uri;
  private String prefLabel;
  private String regionCode;
  
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
  
  @XmlElement(name="regionCode")
  public String getRegionCode()
  {
    return regionCode;
  }
  
  public void setRegionCode(String regionCode)
  {
    this.regionCode = regionCode;
  }
}
