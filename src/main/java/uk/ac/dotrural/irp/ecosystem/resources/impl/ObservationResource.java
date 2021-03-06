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

@Path("/observation")
@Scope("request")
public class ObservationResource implements RESTFulSPARQL
{
  @Context
  private UriInfo uriInfo;
  
  private SPARQLEndpoint observationEndpoint;
  
  public void setObservationEndpoint(SPARQLEndpoint observationsEndpoint)
  {
    this.observationEndpoint = observationsEndpoint;
  }
  
  public SystemMessage init(ServiceInitialiser si)
  {
    return observationEndpoint.init(uriInfo, si);
  }

  public void update(Query query)
  {
    observationEndpoint.update(query);
  }

  public String query(Query query)
  {
    return Util.resultsetToString(observationEndpoint.query(query));
  }

  public EndpointInfo info()
  {
    return observationEndpoint.info();
  }
}
