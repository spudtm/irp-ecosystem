package uk.ac.dotrural.irp.ecosystem.models.jaxb.system;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ServiceInitialiser
{
  private String uri;
  private String servicename;
  private String sparql_framework;

  @XmlElement(name="uri")
  public String getUri()
  {
    return this.uri;
  }

  public void setUri(String uri)
  {
    this.uri = uri;
  }

  @XmlElement(name="servicename")
  public String getServicename()
  {
    return servicename;
  }

  public void setServicename(String servicename)
  {
    this.servicename = servicename;
  }

  @XmlElement(name="sparql_framework")
  public String getSparqlFramework()
  {
    return sparql_framework;
  }

  public void setSparqlFramework(String sparqlFramework)
  {
    this.sparql_framework = sparqlFramework;
  }
}
