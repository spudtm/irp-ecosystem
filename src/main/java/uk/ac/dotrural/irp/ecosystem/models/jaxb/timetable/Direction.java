package uk.ac.dotrural.irp.ecosystem.models.jaxb.timetable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Direction
{
  private String direction;
  private String directionDescription;
  
  @XmlElement(name="direction")
  public String getDirection()
  {
    return direction;
  }

  public void setDirection(String direction)
  {
    this.direction = direction;
  }

  @XmlElement(name="directionDescription")
  public String getDirectionDescription()
  {
    return directionDescription;
  }

  public void setDirectionDescription(String directionDescription)
  {
    this.directionDescription = directionDescription;
  }
}
