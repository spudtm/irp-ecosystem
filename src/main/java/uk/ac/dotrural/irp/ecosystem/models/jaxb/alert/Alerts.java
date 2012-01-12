package uk.ac.dotrural.irp.ecosystem.models.jaxb.alert;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Alerts
{
  private Alert [] alerts;

  @XmlElement(name="alerts")
  public Alert[] getAlerts()
  {
    return alerts;
  }

  public void setAlerts(Alert[] alerts)
  {
    this.alerts = alerts;
  }
}
