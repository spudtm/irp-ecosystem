package uk.ac.dotrural.irp.ecosystem.models.jaxb.transport;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Regions
{
  private List<Region> regions;

  public Regions()
  {}
  
  public Regions(List<Region> regions)
  {
    this.regions = regions;
  }

  @XmlElement(name="regions")
  public List<Region> getRegions()
  {
    return regions;
  }

  public void setRegions(List<Region> regions)
  {
    this.regions = regions;
  }
}
