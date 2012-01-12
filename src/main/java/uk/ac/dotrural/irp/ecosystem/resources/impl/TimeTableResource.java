package uk.ac.dotrural.irp.ecosystem.resources.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
import uk.ac.dotrural.irp.ecosystem.models.jaxb.timetable.BusLocations;
import uk.ac.dotrural.irp.ecosystem.models.jaxb.timetable.Direction;
import uk.ac.dotrural.irp.ecosystem.models.jaxb.timetable.Directions;
import uk.ac.dotrural.irp.ecosystem.models.jaxb.timetable.Line;
import uk.ac.dotrural.irp.ecosystem.models.jaxb.timetable.Lines;
import uk.ac.dotrural.irp.ecosystem.models.jaxb.timetable.Location;
import uk.ac.dotrural.irp.ecosystem.queries.timetable.TimetableQueries;
import uk.ac.dotrural.irp.ecosystem.resources.RESTFulSPARQL;
import uk.ac.dotrural.irp.ecosystem.resources.support.reporters.ExceptionReporter;
import uk.ac.dotrural.irp.ecosystem.services.SPARQLEndpoint;
import uk.ac.dotrural.irp.ecosystem.util.Util;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

@Path("/timetable")
@Scope("request")
public class TimeTableResource implements RESTFulSPARQL
{
  @Context
  private UriInfo uriInfo;
  
  private SPARQLEndpoint timeTableEndpoint;
  
  public void setTimeTableEndpoint(SPARQLEndpoint timeTablesEndpoint)
  {
    this.timeTableEndpoint = timeTablesEndpoint;
  }
  
  public SystemMessage init(ServiceInitialiser si)
  {
    return timeTableEndpoint.init(uriInfo, si);
  }

  public void update(Query query)
  {
    timeTableEndpoint.update(query);
  }

  public String query(Query query)
  {
    return Util.resultsetToString(timeTableEndpoint.query(query));
  }

  public EndpointInfo info()
  {
    return timeTableEndpoint.info();
  }
  
  @GET
  @Produces({MediaType.APPLICATION_JSON})
  @Path("get{Line}InAdminArea")
  public Lines getRoutesInAdminArea(@PathParam("Line") String line,
                                    @DefaultValue("") @QueryParam("adminAreaUri") String adminAreaUri, 
                                    @DefaultValue("false") @QueryParam("includeDirections") boolean includeDirections)
  {
    line = line.trim();
    adminAreaUri = adminAreaUri.trim();
    
    if(adminAreaUri.equals(""))
      throw new ExceptionReporter(new NullPointerException("'adminAreaUri' is NULL or empty."));
    
    String query = "";
    if(line.equals("Route"))
      query = TimetableQueries.getRoutesInAdminAreaQuery(adminAreaUri, includeDirections);
    else
      query = TimetableQueries.getServicesInAdminAreaQuery(adminAreaUri, includeDirections);
      
    Query sparqlQuery = new Query(query);
    ResultSet results = timeTableEndpoint.query(sparqlQuery);
    
    List<Line> lines = new ArrayList<Line>();
    List<String> vars = results.getResultVars();
    
    while(results.hasNext())
    {
      QuerySolution solution = results.next();
      
      String currentLineURI = Util.getNodeValue(solution.get(vars.get(0))).trim();
      Line route = Util.contains(lines, currentLineURI);
      addRoute(lines,route,vars,solution);
    }

    return (new Lines(lines));
  }
  
  @GET
  @Produces({MediaType.APPLICATION_JSON})
  @Path("get{Line}Directions")
  public Directions getDirections(@PathParam("Line") String line,
                                  @DefaultValue("") @QueryParam("lineUri") String lineUri)
  {
    line = line.trim();
    lineUri = lineUri.trim();
    
    if(lineUri.equals(""))
      throw new ExceptionReporter(new NullPointerException(line + " 'uri' is NULL or empty."));

    String query = "";
    if(line.equals("Route"))
      query = TimetableQueries.getRouteDirectionsQuery(lineUri);
    else
      query = TimetableQueries.getServiceDirectionsQuery(lineUri);
    
    Query sparqlQuery = new Query(query);
    ResultSet results = timeTableEndpoint.query(sparqlQuery);

    List<Direction> directions = new ArrayList<Direction>();
    List<String> vars = results.getResultVars();
    while(results.hasNext())
    {
      QuerySolution solution = results.next();
      
      Direction direction = new Direction();
      direction.setDirection(Util.getNodeValue(solution.get(vars.get(0))).trim());
      direction.setDirectionDescription(Util.getNodeValue(solution.get(vars.get(1))).trim());
      
      directions.add(direction);
    }
    
    return new Directions(directions);
  }

  @GET
  @Produces({MediaType.APPLICATION_JSON})
  @Path("getServicesOnRoute")
  public Lines getServicesOnRoute(@DefaultValue("") @QueryParam("lineUri") String lineUri)
  {
    lineUri = lineUri.trim();
    
    if(lineUri.equals(""))
      throw new ExceptionReporter(new NullPointerException("'routeUri' is NULL or empty."));
        
    String query = TimetableQueries.getServicesOnRouteQuery(lineUri);
    
    Query sparqlQuery = new Query(query);
    ResultSet servicesOnRoute = timeTableEndpoint.query(sparqlQuery);
    
    List<Line> lines = new ArrayList<Line>();
    List<String> vars = servicesOnRoute.getResultVars();
    while(servicesOnRoute.hasNext())
    {
      QuerySolution solution = servicesOnRoute.next();
      
      Line line = new Line();
      line.setUri(Util.getNodeValue(solution.get(vars.get(0))).trim());
      line.setLabel(Util.getNodeValue(solution.get(vars.get(1))).trim());
      
      lines.add(line);
    }

    return new Lines(lines);
  }
  
  @GET
  @Produces({MediaType.APPLICATION_JSON})
  @Path("getBusLocationsOn{Line}")
  public BusLocations getBusLocations(@PathParam("Line") String line,
                                      @DefaultValue("") @QueryParam("lineUri") String lineUri,
                                      @DefaultValue("inbound") @QueryParam("direction") String direction)
  {
    line = line.trim();
    lineUri = lineUri.trim();
    direction = direction.trim();
    
    if(lineUri.equals(""))
      throw new ExceptionReporter(new NullPointerException(line + " 'uri' is NULL or empty."));

    String query = "";
    if(line.equals("Route"))
      query = TimetableQueries.getBusLocationsOnRouteQuery(lineUri, direction.equals("inbound"));
    else
      query = TimetableQueries.getBusLocationsOnServiceQuery(lineUri, direction.equals("inbound"));
    
    Query sparqlQuery = new Query(query);
    ResultSet busLocationsOnRoute = timeTableEndpoint.query(sparqlQuery);
    
    List<Location> busLocations = new ArrayList<Location>();
    List<String> vars = busLocationsOnRoute.getResultVars();
    while(busLocationsOnRoute.hasNext())
    {
      QuerySolution solution = busLocationsOnRoute.next();
      
      Location location = new Location();
      location.setTime(Util.getNodeValue(solution.get(vars.get(0))).trim());
      location.setEasting(Integer.parseInt(Util.getNodeValue(solution.get(vars.get(0))).trim()));
      location.setWesting(Integer.parseInt(Util.getNodeValue(solution.get(vars.get(0))).trim()));
      location.setLongitude(Integer.parseInt(Util.getNodeValue(solution.get(vars.get(0))).trim()));
      location.setLatitude(Integer.parseInt(Util.getNodeValue(solution.get(vars.get(0))).trim()));
      
      busLocations.add(location);
    }
    
    return new BusLocations(busLocations);
  }

  private void addRoute(List<Line> lines, 
                        Line line, 
                        List<String> vars, 
                        QuerySolution solution)
  {
    List<Direction> directions = null;
    if(line==null)
    {
      line = new Line();
      line.setUri(Util.getNodeValue(solution.get(vars.get(0))).trim());
      line.setLabel(Util.getNodeValue(solution.get(vars.get(1))).trim());
      line.setAltLabel(Util.getNodeValue(solution.get(vars.get(2))).trim());
      
      directions = new ArrayList<Direction>();
      addDirection( directions,
                    Util.getNodeValue(solution.get(vars.get(3))).trim(),
                    Util.getNodeValue(solution.get(vars.get(4))).trim());
      
      line.setDirections(new Directions(directions));
      lines.add(line);
    }
    else
    {
      directions = line.getDirections().getDirections();
      addDirection( directions,
                    Util.getNodeValue(solution.get(vars.get(3))).trim(),
                    Util.getNodeValue(solution.get(vars.get(4))).trim());
      
      line.setDirections(new Directions(directions));
    }
  }
  
  private void addDirection(List<Direction> directions, 
                            String direction,
                            String directionDescription)
  {
    Direction d = new Direction();
    d.setDirection(direction);
    d.setDirectionDescription(directionDescription);
    
    directions.add(d);
  }
}
