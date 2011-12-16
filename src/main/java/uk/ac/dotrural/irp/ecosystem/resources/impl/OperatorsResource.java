package uk.ac.dotrural.irp.ecosystem.resources.impl;

import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.springframework.context.annotation.Scope;

import uk.ac.dotrural.irp.ecosystem.models.jaxb.system.EndpointInfo;
import uk.ac.dotrural.irp.ecosystem.models.jaxb.system.Query;
import uk.ac.dotrural.irp.ecosystem.models.jaxb.system.ServiceInitialiser;
import uk.ac.dotrural.irp.ecosystem.models.jaxb.system.SystemMessage;
import uk.ac.dotrural.irp.ecosystem.resources.RESTFulSPARQL;
import uk.ac.dotrural.irp.ecosystem.services.SPARQLEndpoint;
import uk.ac.dotrural.irp.ecosystem.util.Util;

@Path("/operators")
@Scope("request")
public class OperatorsResource implements RESTFulSPARQL
{
  @Context
  private UriInfo uriInfo;
  
  private SPARQLEndpoint operatorsEndpoint;
  
  public void setOperatorsEndpoint(SPARQLEndpoint operatorsEndpoint)
  {
    this.operatorsEndpoint = operatorsEndpoint;
  }
  
  public SystemMessage init(ServiceInitialiser si)
  {
    return operatorsEndpoint.init(uriInfo, si);
  }

  public void update(Query query)
  {
    operatorsEndpoint.update(query);
  }

  public String query(Query query)
  {
    return Util.resultsetToString(operatorsEndpoint.query(query));
  }

  public EndpointInfo info()
  {
    return operatorsEndpoint.info();
  }
}
