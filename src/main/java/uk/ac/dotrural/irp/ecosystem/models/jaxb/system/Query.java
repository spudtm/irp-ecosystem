package uk.ac.dotrural.irp.ecosystem.models.jaxb.system;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Query
{
  private String query;
  
  public Query()
  {}
  
  public Query(String query)
  {
    this.query = query;
  }

  @XmlElement(name="query")
  public String getQuery()
  {
    return query;
  }

  public void setQuery(String query)
  {
    this.query = query;
  }
}
