package uk.ac.dotrural.irp.ecosystem.models.jaxb.transport;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AdminArea
{
  private String uri;
  private String atcoAreaCode;
  private String adminAreaCode;
  private String prefLabel;
  
  @XmlElement(name="uri")
  public String getUri()
  {
    return uri;
  }
  
  public void setUri(String uri)
  {
    this.uri = uri;
  }
  
  @XmlElement(name="atcoAreaCode")
  public String getAtcoAreaCode()
  {
    return atcoAreaCode;
  }
  
  public void setAtcoAreaCode(String atcoAreaCode)
  {
    this.atcoAreaCode = atcoAreaCode;
  }
  
  @XmlElement(name="adminAreaCode")
  public String getAdminAreaCode()
  {
    return adminAreaCode;
  }
  
  public void setAdminAreaCode(String adminAreaCode)
  {
    this.adminAreaCode = adminAreaCode;
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
}
