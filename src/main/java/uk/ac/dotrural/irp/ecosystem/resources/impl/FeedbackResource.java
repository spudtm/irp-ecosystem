package uk.ac.dotrural.irp.ecosystem.resources.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.Consumes;
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

import uk.ac.dotrural.irp.ecosystem.models.jaxb.feedback.Feedback;
import uk.ac.dotrural.irp.ecosystem.models.jaxb.feedback.FeedbackPayload;
import uk.ac.dotrural.irp.ecosystem.models.jaxb.feedback.Feedbacks;
import uk.ac.dotrural.irp.ecosystem.models.jaxb.journey.Journey;
import uk.ac.dotrural.irp.ecosystem.models.jaxb.system.EndpointInfo;
import uk.ac.dotrural.irp.ecosystem.models.jaxb.system.Query;
import uk.ac.dotrural.irp.ecosystem.models.jaxb.system.ServiceInitialiser;
import uk.ac.dotrural.irp.ecosystem.models.jaxb.system.SystemMessage;
import uk.ac.dotrural.irp.ecosystem.models.jaxb.user.User;
import uk.ac.dotrural.irp.ecosystem.queries.feedback.FeedbackQueries;
import uk.ac.dotrural.irp.ecosystem.queries.feedback.QueryReader;
import uk.ac.dotrural.irp.ecosystem.resources.RESTFulSPARQL;
import uk.ac.dotrural.irp.ecosystem.resources.support.reporters.ExceptionReporter;
import uk.ac.dotrural.irp.ecosystem.services.SPARQLEndpoint;
import uk.ac.dotrural.irp.ecosystem.util.Util;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

@Path("/feedback")
@Scope("request")
public class FeedbackResource implements RESTFulSPARQL
{
  @Context
  private UriInfo uriInfo;
  
  private SPARQLEndpoint feedbackEndpoint;
  
  public void setFeedbackEndpoint(SPARQLEndpoint operatorsEndpoint)
  {
    this.feedbackEndpoint = operatorsEndpoint;
  }
  
  public SystemMessage init(ServiceInitialiser si)
  {
    return feedbackEndpoint.init(uriInfo, si);
  }

  public void update(Query query)
  {
    feedbackEndpoint.update(query);
  }

  public String query(Query query)
  {
    return Util.resultsetToString(feedbackEndpoint.query(query));
  }

  public EndpointInfo info()
  {
    return feedbackEndpoint.info();
  }
  
  @POST
  @Consumes({MediaType.APPLICATION_JSON})
  @Produces({MediaType.APPLICATION_JSON})
  @Path("create")
  public Feedback create(FeedbackPayload feedbackPayload)
  {
    if(feedbackPayload == null)
      throw new ExceptionReporter(new NullPointerException("No 'FeedbackPayload' given."));
    
    if(feedbackPayload.getUserUri() == null || feedbackPayload.getUserUri().trim().equals(""))
      throw new ExceptionReporter(new NullPointerException("User URI needed to create the feedback."));
    if(feedbackPayload.getMessage() == null || feedbackPayload.getMessage().trim().equals(""))
      throw new ExceptionReporter(new NullPointerException("A message needed to create the feedback."));
    if(feedbackPayload.getAuthenticationToken() == null || feedbackPayload.getAuthenticationToken().trim().equals(""))
      throw new ExceptionReporter(new NullPointerException("An authentication token needed to create the feedback."));
    
    String feedbackUri = QueryReader.getString("FeedbackQueries.baseNS") + UUID.randomUUID().toString();
    String query = FeedbackQueries.getCreateFeedbackUpdate(feedbackPayload.getUserUri(), 
                                                           (feedbackPayload.getJourneyUri()==null)?"":feedbackPayload.getJourneyUri(), 
                                                           feedbackPayload.getMessage(), 
                                                           feedbackUri);
    Query sparqlQuery = new Query(query);
    feedbackEndpoint.update(sparqlQuery);

    Feedback feedback = new Feedback();
    feedback.setUri(feedbackUri);
    return feedback;
  }

  @GET
  @Produces({MediaType.APPLICATION_JSON})
  @Path("get")
  public Feedback get(@DefaultValue("") @QueryParam("feedbackUri") String feedbackUri,
                              @DefaultValue("") @QueryParam("authenticationToken") String authenticationToken)
  {
    feedbackUri = feedbackUri.trim();
    authenticationToken = authenticationToken.trim();
    
    if(feedbackUri.equals(""))
      throw new ExceptionReporter(new NullPointerException("'feedbackUri' is NULL or empty."));
    if(authenticationToken.equals(""))
      throw new ExceptionReporter(new NullPointerException("'authenticationToken' is NULL or empty."));
    
    String query = FeedbackQueries.getFeedbackQuery(feedbackUri);
    Query sparqlQuery = new Query(query);
    
    ResultSet results = feedbackEndpoint.query(sparqlQuery);

    Feedback feedback = new Feedback();
    List<String> vars = results.getResultVars();
    
    while(results.hasNext())
    {
      QuerySolution solution = results.next();
      
      feedback.setUri(feedbackUri);
      feedback.setUser(new User(Util.getNodeValue(solution.get(vars.get(0))).trim()));
      feedback.setJourney(new Journey(Util.getNodeValue(solution.get(vars.get(1))).trim()));
      feedback.setMessage(Util.getNodeValue(solution.get(vars.get(2))).trim());
    }
    
    return feedback;
  }

  @POST
  @Consumes({MediaType.APPLICATION_JSON})
  @Path("delete")
  public void delete(FeedbackPayload feedbackPayload)
  {
    if(feedbackPayload == null)
      throw new ExceptionReporter(new NullPointerException("No 'FeedbackPayload' given."));
    
    if(feedbackPayload.getFeedbackUri() == null || feedbackPayload.getFeedbackUri().trim().equals(""))
      throw new ExceptionReporter(new NullPointerException("Feedback URI needed to delete the feedback."));
    if(feedbackPayload.getAuthenticationToken() == null || feedbackPayload.getAuthenticationToken().trim().equals(""))
      throw new ExceptionReporter(new NullPointerException("An authentication token needed to delete the feedback."));
    
    String query = FeedbackQueries.getDeleteFeedbackUpdate(feedbackPayload.getFeedbackUri().trim());
    Query sparqlQuery = new Query(query);
    feedbackEndpoint.update(sparqlQuery);
  }

  @POST
  @Consumes({MediaType.APPLICATION_JSON})
  @Path("update")
  public void update(FeedbackPayload feedbackPayload)
  {
    if(feedbackPayload == null)
      throw new ExceptionReporter(new NullPointerException("No 'FeedbackPayload' given."));
    
    if(feedbackPayload.getFeedbackUri() == null || feedbackPayload.getFeedbackUri().trim().equals(""))
      throw new ExceptionReporter(new NullPointerException("Feedback URI needed to update the feedback."));
    if(feedbackPayload.getMessage() == null || feedbackPayload.getMessage().trim().equals(""))
      throw new ExceptionReporter(new NullPointerException("A message needed to update the feedback."));
    if(feedbackPayload.getAuthenticationToken() == null || feedbackPayload.getAuthenticationToken().trim().equals(""))
      throw new ExceptionReporter(new NullPointerException("An authentication token needed to update the feedback."));

    // TO DO: update query needed
  }
  
  @GET
  @Produces({MediaType.APPLICATION_JSON})
  @Path("getForUser")
  public Feedbacks getForUser(@DefaultValue("") @QueryParam("userUri") String userUri,
                                      @DefaultValue("") @QueryParam("authenticationToken") String authenticationToken)
  {
    userUri = userUri.trim();
    authenticationToken = authenticationToken.trim();
    
    if(userUri.equals(""))
      throw new ExceptionReporter(new NullPointerException("'userUri' is NULL or empty."));
    if(authenticationToken.equals(""))
      throw new ExceptionReporter(new NullPointerException("'authenticationToken' is NULL or empty."));
    
    String query = FeedbackQueries.getFeedbackForUserQuery(userUri);
    Query sparqlQuery = new Query(query);
    
    ResultSet results = feedbackEndpoint.query(sparqlQuery);

    List<Feedback> feedbacks = new ArrayList<Feedback>();
    List<String> vars = results.getResultVars();
    
    while(results.hasNext())
    {
      QuerySolution solution = results.next();
      
      Feedback feedback = new Feedback();
      feedback.setMessage(Util.getNodeValue(solution.get(vars.get(0))).trim());
      
      feedbacks.add(feedback);
    }
    
    return new Feedbacks(feedbacks);
  }
}
