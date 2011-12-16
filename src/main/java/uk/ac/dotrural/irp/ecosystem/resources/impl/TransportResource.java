package uk.ac.dotrural.irp.ecosystem.resources.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.springframework.context.annotation.Scope;

import uk.ac.dotrural.irp.ecosystem.models.jaxb.system.EndpointInfo;
import uk.ac.dotrural.irp.ecosystem.models.jaxb.system.Query;
import uk.ac.dotrural.irp.ecosystem.models.jaxb.system.ServiceInitialiser;
import uk.ac.dotrural.irp.ecosystem.models.jaxb.system.SystemMessage;
import uk.ac.dotrural.irp.ecosystem.models.jaxb.transport.AdminArea;
import uk.ac.dotrural.irp.ecosystem.models.jaxb.transport.AdminAreas;
import uk.ac.dotrural.irp.ecosystem.models.jaxb.transport.BusStop;
import uk.ac.dotrural.irp.ecosystem.models.jaxb.transport.BusStops;
import uk.ac.dotrural.irp.ecosystem.models.jaxb.transport.Localities;
import uk.ac.dotrural.irp.ecosystem.models.jaxb.transport.Locality;
import uk.ac.dotrural.irp.ecosystem.models.jaxb.transport.Region;
import uk.ac.dotrural.irp.ecosystem.models.jaxb.transport.Regions;
import uk.ac.dotrural.irp.ecosystem.queries.transport.NaptanNptgQueries;
import uk.ac.dotrural.irp.ecosystem.resources.RESTFulSPARQL;
import uk.ac.dotrural.irp.ecosystem.resources.support.reporters.ExceptionReporter;
import uk.ac.dotrural.irp.ecosystem.services.SPARQLEndpoint;
import uk.ac.dotrural.irp.ecosystem.util.Util;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

@Path("/transport")
@Scope("request")
public class TransportResource implements RESTFulSPARQL
{
  @Context
  private UriInfo uriInfo;
  
  private SPARQLEndpoint naptanEndpoint;
  
  public void setNaptanEndpoint(SPARQLEndpoint naptanEndpoint)
  {
    this.naptanEndpoint = naptanEndpoint;
  }
  
  public SystemMessage init(ServiceInitialiser si)
  {
    return naptanEndpoint.init(uriInfo, si);
  }

  public void update(Query query)
  {
    naptanEndpoint.update(query);
  }

  public String query(Query query)
  {
    return Util.resultsetToString(naptanEndpoint.query(query));
  }

  public EndpointInfo info()
  {
    return naptanEndpoint.info();
  }
  
  @GET
  @Produces({MediaType.APPLICATION_JSON})
  @Path("getNptgRegions")
  public Regions getNptgRegions()
  {
    String query = NaptanNptgQueries.getNptgRegionsQuery();
      
    Query sparqlQuery = new Query(query);
    ResultSet results = naptanEndpoint.query(sparqlQuery);

    List<Region> regions = new ArrayList<Region>();
    List<String> vars = results.getResultVars();
    
    while(results.hasNext())
    {
      QuerySolution solution = results.next();
      
      Region region = new Region();
      region.setUri(Util.getNodeValue(solution.get(vars.get(0))).trim());
      region.setPrefLabel(Util.getNodeValue(solution.get(vars.get(1))).trim());
      region.setRegionCode(Util.getNodeValue(solution.get(vars.get(2))).trim());
      
      regions.add(region);
    }
    
    return new Regions(regions);
  }

  @GET
  @Produces({MediaType.APPLICATION_JSON})
  @Path("getNptgAdminAreas")
  public AdminAreas getNptgAdminAreas(@DefaultValue("") @QueryParam("regionUri") String regionUri)
  {
    regionUri = regionUri.trim();
    
    if(regionUri.equals(""))
      throw new ExceptionReporter(new NullPointerException("'regionUri' is NULL or empty."));
        
    String query = NaptanNptgQueries.getNptgAdminAreasQuery(regionUri);
    
    Query sparqlQuery = new Query(query);
    ResultSet results = naptanEndpoint.query(sparqlQuery);
    
    List<AdminArea> adminAreas = new ArrayList<AdminArea>();
    List<String> vars = results.getResultVars();
    
    while(results.hasNext())
    {
      QuerySolution solution = results.next();
      
      AdminArea adminArea = new AdminArea();
      adminArea.setUri(Util.getNodeValue(solution.get(vars.get(0))).trim());
      adminArea.setAtcoAreaCode(Util.getNodeValue(solution.get(vars.get(1))).trim());
      adminArea.setAdminAreaCode(Util.getNodeValue(solution.get(vars.get(2))).trim());
      adminArea.setPrefLabel(Util.getNodeValue(solution.get(vars.get(3))).trim());
      
      adminAreas.add(adminArea);
    }
    
    return new AdminAreas(adminAreas);
  }
  
  @GET
  @Produces({MediaType.APPLICATION_JSON})
  @Path("getNptgLocalities")
  public Localities getNptgLocalities(@DefaultValue("") @QueryParam("adminAreaUri") String adminAreaUri)
  {
    adminAreaUri = adminAreaUri.trim();
    
    if(adminAreaUri.equals(""))
      throw new ExceptionReporter(new NullPointerException("'adminAreaUri' is NULL or empty."));

    String query = NaptanNptgQueries.getNptgLocalitiesQuery(adminAreaUri);
    
    Query sparqlQuery = new Query(query);
    ResultSet results = naptanEndpoint.query(sparqlQuery);
    
    List<Locality> localities = new ArrayList<Locality>();
    List<String> vars = results.getResultVars();
    
    while(results.hasNext())
    {
      QuerySolution solution = results.next();
      
      Locality locality = new Locality();
      locality.setUri(Util.getNodeValue(solution.get(vars.get(0))).trim());
      locality.setPrefLabel(Util.getNodeValue(solution.get(vars.get(1))).trim());
      locality.setAltLabel(Util.getNodeValue(solution.get(vars.get(2))).trim());
      locality.setNptgLocalityCode(Util.getNodeValue(solution.get(vars.get(3))).trim());
      
      localities.add(locality);
    }
    
    return new Localities(localities);
  }
  
  @GET
  @Produces({MediaType.APPLICATION_JSON})
  @Path("getBusStopPointsInLocality")
  public BusStops getBusStopPointsInLocality(@DefaultValue("") @QueryParam("localityUri") String localityUri)
  {
    localityUri = localityUri.trim();
    
    if(localityUri.equals(""))
      throw new ExceptionReporter(new NullPointerException("'adminAreaUri' is NULL or empty."));

    String query = NaptanNptgQueries.getBusStopPointsInLocalityQuery(localityUri);
    
    Query sparqlQuery = new Query(query);
    ResultSet results = naptanEndpoint.query(sparqlQuery);
    
    List<BusStop> busStops = new ArrayList<BusStop>();
    List<String> vars = results.getResultVars();
    
    while(results.hasNext())
    {
      QuerySolution solution = results.next();
      
      BusStop busStop = new BusStop();
      busStop.setUri(Util.getNodeValue(solution.get(vars.get(0))).trim());
      busStop.setPrefLabel(Util.getNodeValue(solution.get(vars.get(1))).trim());
      busStop.setStreet(Util.getNodeValue(solution.get(vars.get(2))).trim());
      
      busStops.add(busStop);
    }  
    
    return new BusStops(busStops);
  }
}
