package uk.ac.dotrural.irp.ecosystem.models.jaxb.timetable;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Lines
{
  private List<Line> lines;
  
  public Lines()
  {}
  
  public Lines(List<Line> lines)
  {
    this.lines = lines;
  }

  @XmlElement(name="lines")
  public List<Line> getRoute()
  {
    return lines;
  }

  public void setRoute(List<Line> route)
  {
    lines = route;
  }
}
