package uk.ac.dotrural.irp.ecosystem.models.jaxb.timetable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Line
{
  private String uri;
  private String label;
  private String altLabel;
  private Directions directions;
  
  public Line()
  {}
  
  public Line(String uri)
  {
    this.uri = uri;
  }

  @XmlElement(name="uri")
  public String getUri()
  {
    return uri;
  }

  public void setUri(String uri)
  {
    this.uri = uri;
  }

  @XmlElement(name="label")
  public String getLabel()
  {
    return label;
  }
  
  public void setLabel(String label)
  {
    this.label = label;
  }
  
  @XmlElement(name="altLabel")
  public String getAltLabel()
  {
    return altLabel;
  }
  
  public void setAltLabel(String altLabel)
  {
    this.altLabel = altLabel;
  }

  public Directions getDirections()
  {
    return directions;
  }

  public void setDirections(Directions directions)
  {
    this.directions = directions;
  }
}
