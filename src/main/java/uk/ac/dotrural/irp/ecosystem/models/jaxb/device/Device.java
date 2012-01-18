package uk.ac.dotrural.irp.ecosystem.models.jaxb.device;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Device
{
  private String uri;
  private String type;
  private Sensors sensors;
  private String operatingSystem;
  private String operatingSystemVersion;
  private String uniqueId;
  
  public Device()
  {}
  
  public Device(String uri)
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
  
  @XmlElement(name="type")
  public String getType()
  {
    return type;
  }

  public void setType(String type)
  {
    this.type = type;
  }

  @XmlElement(name="sensors")
  public Sensors getSensors()
  {
    return sensors;
  }
  
  public void setSensors(Sensors sensors)
  {
    this.sensors = sensors;
  }

  @XmlElement(name="operatingSystem")
  public String getOperatingSystem()
  {
    return operatingSystem;
  }

  public void setOperatingSystem(String operatingSystem)
  {
    this.operatingSystem = operatingSystem;
  }

  @XmlElement(name="operatingSystemVersion")
  public String getOperatingSystemVersion()
  {
    return operatingSystemVersion;
  }

  public void setOperatingSystemVersion(String operatingSystemVersion)
  {
    this.operatingSystemVersion = operatingSystemVersion;
  }

  @XmlElement(name="uniqueId")
  public String getUniqueId()
  {
    return uniqueId;
  }

  public void setUniqueId(String uniqueId)
  {
    this.uniqueId = uniqueId;
  }
  
  
}
