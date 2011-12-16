package uk.ac.dotrural.irp.ecosystem.models.jaxb.timetable;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BusLocations
{
  private List<Location> locations;

  public BusLocations()
  {}
  
  public BusLocations(List<Location> locations)
  {
    this.locations = locations;
  }
  
  @XmlElement(name="locations")
  public List<Location> getLocations()
  {
    return locations;
  }

  public void setLocations(List<Location> locations)
  {
    this.locations = locations;
  }
}
