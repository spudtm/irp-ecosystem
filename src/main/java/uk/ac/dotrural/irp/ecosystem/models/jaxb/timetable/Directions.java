package uk.ac.dotrural.irp.ecosystem.models.jaxb.timetable;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Directions
{
  private List<Direction> directions;

  public Directions()
  {}
  
  public Directions(List<Direction> directions)
  {
    this.directions = directions;
  }
  
  @XmlElement(name="directions")
  public List<Direction> getDirections()
  {
    return directions;
  }

  public void setDirections(List<Direction> directions)
  {
    this.directions = directions;
  }
}
