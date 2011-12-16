package uk.ac.dotrural.irp.ecosystem.models.jaxb.transport;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Localities
{
  private List<Locality> localities;

  public Localities()
  {}
  
  public Localities(List<Locality> localities)
  {
    this.localities = localities;
  }
  
  @XmlElement(name="localities")
  public List<Locality> getLocalities()
  {
    return localities;
  }

  public void setLocalities(List<Locality> localities)
  {
    this.localities = localities;
  }
}
