package uk.ac.dotrural.irp.ecosystem.models.jaxb.system;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EndpointInfo
{
  private String sparql_framework;
  private String serviceURI;
  private String updateURI;
  private String queryURI;
  
  @XmlElement(name="sparql_framework")
  public String getSparql_framework()
  {
    return sparql_framework;
  }
  
  public void setSparql_framework(String sparql_framework)
  {
    this.sparql_framework = sparql_framework;
  }
  
  @XmlElement(name="serviceURI")
  public String getServiceURI()
  {
    return serviceURI;
  }
  
  public void setServiceURI(String serviceURI)
  {
    this.serviceURI = serviceURI;
  }
  
  @XmlElement(name="updateURI")
  public String getUpdateURI()
  {
    return updateURI;
  }
  
  public void setUpdateURI(String updateURI)
  {
    this.updateURI = updateURI;
  }
  
  @XmlElement(name="queryURI")
  public String getQueryURI()
  {
    return queryURI;
  }
  
  public void setQueryURI(String queryURI)
  {
    this.queryURI = queryURI;
  }
}
