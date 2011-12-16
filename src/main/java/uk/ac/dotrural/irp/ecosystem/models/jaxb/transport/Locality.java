package uk.ac.dotrural.irp.ecosystem.models.jaxb.transport;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Locality
{
  private String uri;
  private String prefLabel;
  private String altLabel;
  private String nptgLocalityCode;
  
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
  
  @XmlElement(name="altLabel")
  public String getAltLabel()
  {
    return altLabel;
  }
  
  public void setAltLabel(String altLabel)
  {
    this.altLabel = altLabel;
  }
  
  @XmlElement(name="nptgLocalityCode")
  public String getNptgLocalityCode()
  {
    return nptgLocalityCode;
  }
  
  public void setNptgLocalityCode(String nptgLocalityCode)
  {
    this.nptgLocalityCode = nptgLocalityCode;
  }
}
