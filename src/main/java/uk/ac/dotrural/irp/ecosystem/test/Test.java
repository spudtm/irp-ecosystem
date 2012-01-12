package uk.ac.dotrural.irp.ecosystem.test;

import uk.ac.dotrural.irp.ecosystem.queries.alert.ServiceDays;
import uk.ac.dotrural.irp.ecosystem.util.Util;

public class Test
{
  public static void main(String[] args)
  {
    boolean [] bDays = {true,true,true,false,true,true,true,false};
    ServiceDays [] days = Util.getServiceDays(bDays);
    for(ServiceDays day:days)
      System.out.format("  %s : %d\n",day.toString(),day.ordinal());
  }
}
