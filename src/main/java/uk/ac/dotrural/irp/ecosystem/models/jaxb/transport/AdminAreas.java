package uk.ac.dotrural.irp.ecosystem.models.jaxb.transport;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AdminAreas
{
  private List<AdminArea> adminAreas;
  
  public AdminAreas()
  {}
  
  public AdminAreas(List<AdminArea> adminAreas)
  {
    this.adminAreas = adminAreas;
  }

  @XmlElement(name="adminAreas")
  public List<AdminArea> getAdminAreas()
  {
    return adminAreas;
  }

  public void setAdminAreas(List<AdminArea> adminAreas)
  {
    this.adminAreas = adminAreas;
  }
}
