package uk.ac.dotrural.irp.ecosystem.resources.impl;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.springframework.context.annotation.Scope;

import uk.ac.dotrural.irp.ecosystem.models.jaxb.device.Device;
import uk.ac.dotrural.irp.ecosystem.models.jaxb.journey.Journey;
import uk.ac.dotrural.irp.ecosystem.models.jaxb.journey.JourneyPayload;
import uk.ac.dotrural.irp.ecosystem.models.jaxb.system.EndpointInfo;
import uk.ac.dotrural.irp.ecosystem.models.jaxb.system.Query;
import uk.ac.dotrural.irp.ecosystem.models.jaxb.system.ServiceInitialiser;
import uk.ac.dotrural.irp.ecosystem.models.jaxb.system.SystemMessage;
import uk.ac.dotrural.irp.ecosystem.models.jaxb.timetable.Line;
import uk.ac.dotrural.irp.ecosystem.models.jaxb.user.User;
import uk.ac.dotrural.irp.ecosystem.queries.journey.BusJourneyQueries;
import uk.ac.dotrural.irp.ecosystem.queries.journey.QueryReader;
import uk.ac.dotrural.irp.ecosystem.resources.RESTFulSPARQL;
import uk.ac.dotrural.irp.ecosystem.resources.support.reporters.ExceptionReporter;
import uk.ac.dotrural.irp.ecosystem.services.SPARQLEndpoint;
import uk.ac.dotrural.irp.ecosystem.util.Util;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

@Path("/journey")
@Scope("request")
public class JourneyResource implements RESTFulSPARQL
{
  @Context
  private UriInfo uriInfo;
  
  private SPARQLEndpoint journeyEndpoint;
  
  public void setJourneyEndpoint(SPARQLEndpoint routesEndpoint)
  {
    this.journeyEndpoint = routesEndpoint;
  }
  
  public SystemMessage init(ServiceInitialiser si)
  {
    return journeyEndpoint.init(uriInfo, si);
  }

  public void update(Query query)
  {
    journeyEndpoint.update(query);
  }

  public String query(Query query)
  {
    return Util.resultsetToString(journeyEndpoint.query(query));
  }

  public EndpointInfo info()
  {
    return journeyEndpoint.info();
  }

  @POST
  @Consumes({MediaType.APPLICATION_JSON})
  @Produces({MediaType.APPLICATION_JSON})
  @Path("create")
  public Journey create(JourneyPayload journeyPayload)
  {
    if(journeyPayload == null)
      throw new ExceptionReporter(new NullPointerException("No journey details given."));
    if(journeyPayload.getAuthenticationToken() == null || journeyPayload.getAuthenticationToken().trim().equals(""))
      throw new ExceptionReporter(new NullPointerException("No authentication token given."));
    if(journeyPayload.getType() == null)
      throw new ExceptionReporter(new NullPointerException("No journey type given."));
    if(journeyPayload.getUserUri() == null || journeyPayload.getUserUri().trim().equals(""))
      throw new ExceptionReporter(new NullPointerException("No 'userUri' given."));
    if(journeyPayload.getDevice() == null)
      throw new ExceptionReporter(new NullPointerException("No 'device' given."));
    
    String uri = QueryReader.getString("JourneyQueries.baseNS") + UUID.randomUUID().toString();
    String query = BusJourneyQueries.getCreateBusJourneyUpdate(journeyPayload.getUserUri(), 
                                                               journeyPayload.getDevice().getUri(), 
                                                               journeyPayload.getType().getRouteUri(), 
                                                               uri);
    Query sparqlQuery = new Query(query);
    journeyEndpoint.update(sparqlQuery);
    
    Journey journey = new Journey();
    journey.setUri(uri);
    journey.setDevice(journeyPayload.getDevice());

    return journey;
  }
  
  @GET
  @Produces({MediaType.APPLICATION_JSON})
  @Path("get")
  public Journey get(@DefaultValue("") @QueryParam("journeyUri") String journeyUri)
  {
    journeyUri = journeyUri.trim();
    
    if(journeyUri.equals(""))
      throw new ExceptionReporter(new NullPointerException("'journeyUri' is needed to retrieve the journey."));
    
    String query = BusJourneyQueries.getBusJourneyQuery(journeyUri);
    Query sparqlQuery = new Query(query);
    ResultSet results = journeyEndpoint.query(sparqlQuery);
    
    List<String> vars = results.getResultVars();
    Journey journey = new Journey();
    
    while(results.hasNext())
    {
      QuerySolution solution = results.next();
      
      journey.setLine(new Line(Util.getNodeValue(solution.get(vars.get(0))).trim()));
      journey.setDevice(new Device(Util.getNodeValue(solution.get(vars.get(1))).trim()));
      journey.setUser(new User(Util.getNodeValue(solution.get(vars.get(2))).trim()));
    }
    
    return journey;
  }
  
  @DELETE
  @Consumes({MediaType.APPLICATION_JSON})
  @Path("delete")
  public void delete(@DefaultValue("") @QueryParam("journeyUri") String journeyUri)
  {
    journeyUri = journeyUri.trim();
    
    if(journeyUri.equals(""))
      throw new ExceptionReporter(new NullPointerException("No journey uri given."));
    
    String query = BusJourneyQueries.getDeleteBusJourneyUpdate(journeyUri);
    Query sparqlQuery = new Query(query);
    journeyEndpoint.update(sparqlQuery);
  }
}
