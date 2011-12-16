package uk.ac.dotrural.irp.ecosystem.models.jaxb.transport;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BusStops
{
  private List<BusStop> busStops;

  public BusStops()
  {}
  
  public BusStops(List<BusStop> busStops)
  {
    this.busStops = busStops;
  }
  
  @XmlElement(name="busStops")
  public List<BusStop> getBusStops()
  {
    return busStops;
  }

  public void setBusStops(List<BusStop> busStops)
  {
    this.busStops = busStops;
  }
}
