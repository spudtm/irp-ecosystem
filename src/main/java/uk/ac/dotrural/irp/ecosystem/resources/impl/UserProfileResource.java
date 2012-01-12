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

import uk.ac.dotrural.irp.ecosystem.models.jaxb.system.EndpointInfo;
import uk.ac.dotrural.irp.ecosystem.models.jaxb.system.Query;
import uk.ac.dotrural.irp.ecosystem.models.jaxb.system.ServiceInitialiser;
import uk.ac.dotrural.irp.ecosystem.models.jaxb.system.SystemMessage;
import uk.ac.dotrural.irp.ecosystem.models.jaxb.user.User;
import uk.ac.dotrural.irp.ecosystem.queries.user.QueryReader;
import uk.ac.dotrural.irp.ecosystem.queries.user.UserQueries;
import uk.ac.dotrural.irp.ecosystem.resources.RESTFulSPARQL;
import uk.ac.dotrural.irp.ecosystem.resources.support.reporters.ExceptionReporter;
import uk.ac.dotrural.irp.ecosystem.services.SPARQLEndpoint;
import uk.ac.dotrural.irp.ecosystem.util.Util;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

@Path("/user-profile")
@Scope("request")
public class UserProfileResource implements RESTFulSPARQL
{
  @Context
  private UriInfo uriInfo;
  
  private SPARQLEndpoint userProfileEndpoint;
  
  public void setUserProfileEndpoint(SPARQLEndpoint userProfilesEndpoint)
  {
    this.userProfileEndpoint = userProfilesEndpoint;
  }
  
  public SystemMessage init(ServiceInitialiser si)
  {
    return userProfileEndpoint.init(uriInfo, si);
  }

  public void update(Query query)
  {
    userProfileEndpoint.update(query);
  }

  public String query(Query query)
  {
    return Util.resultsetToString(userProfileEndpoint.query(query));
  }

  public EndpointInfo info()
  {
    return userProfileEndpoint.info();
  }
  
  @POST
  @Consumes({MediaType.APPLICATION_JSON})
  @Produces({MediaType.APPLICATION_JSON})
  @Path("create")
  public User create(User userDetails)
  {
    if(userDetails == null)
      throw new ExceptionReporter(new NullPointerException("No 'UserDetails' given."));
    
    if(userDetails.getNickname() == null || userDetails.getNickname().trim().equals(""))
      throw new ExceptionReporter(new NullPointerException("Nickname needed to create the user."));
    
    if(userDetails.getEmail() == null || userDetails.getEmail().trim().equals(""))
      throw new ExceptionReporter(new NullPointerException("Email needed to create the user."));
    
    if(userDetails.getPassword() == null || userDetails.getPassword().trim().equals(""))
      throw new ExceptionReporter(new NullPointerException("Password needed to create the user."));
    
    String userUrl = "";
    String query = UserQueries.getExistsQuery(userDetails.getEmail());
    Query sparqlQuery = new Query(query);
    if(!userProfileEndpoint.ask(sparqlQuery))
    {
      userUrl = QueryReader.getString("UserQueries.query.create.baseNS") + UUID.randomUUID().toString();
      query = UserQueries.getCreateUserUpdate(userUrl.trim(), userDetails.getNickname().trim(), userDetails.getEmail().trim(), userDetails.getPassword().trim());
      sparqlQuery = new Query(query);
      userProfileEndpoint.update(sparqlQuery);

      User newUser = new User();
      newUser.setUserUri(userUrl);
      newUser.setExists(!userUrl.equals(""));
      return newUser;
    }
    else
      return get(userDetails.getEmail(),"","");
  }

  @GET
  @Produces({MediaType.APPLICATION_JSON})
  @Path("get")
  public User get(@DefaultValue("") @QueryParam("email") String email,
                  @DefaultValue("") @QueryParam("userUri") String userUri,
                  @DefaultValue("") @QueryParam("authenticationToken") String authenticationToken)
  {
    email = email.trim();
    userUri = userUri.trim();
    authenticationToken = authenticationToken.trim();
    
    if(email.equals("") && userUri.equals("") && authenticationToken.equals(""))
      throw new ExceptionReporter(new NullPointerException("'email' or 'userUri' or 'authenticationToken' is needed to retrieve the user."));
    
    String query = "";
    if(!email.equals(""))
      query = UserQueries.getUserByEmailQuery(email);
    else if(!userUri.equals(""))
      query = UserQueries.getUserByUriQuery(userUri);
    else
      return new User();
      
    Query sparqlQuery = new Query(query);
    ResultSet results = userProfileEndpoint.query(sparqlQuery);
    
    List<String> vars = results.getResultVars();
    User user = new User();
    
    while(results.hasNext())
    {
      QuerySolution solution = results.next();
      
      user.setNickname(Util.getNodeValue(solution.get(vars.get(0))).trim());
      user.setUserUri(Util.getNodeValue(solution.get(vars.get(1))).trim());
      user.setExists(true);
    }
    
    return user;
  }
  
  @DELETE
  @Path("delete")
  public void delete(@DefaultValue("") @QueryParam("userUri") String userUri,
                     @DefaultValue("") @QueryParam("authenticationToken") String authenticationToken)
  {
    userUri = userUri.trim();
    authenticationToken  =authenticationToken.trim();
    
    if(userUri.equals("") && authenticationToken.equals(""))
      throw new ExceptionReporter(new NullPointerException("'email' or 'userUri' or 'authenticationToken' is needed to retrieve the user."));
    
    String query = UserQueries.getDeleteUserUpdate(userUri);
    Query sparqlQuery = new Query(query);
    userProfileEndpoint.update(sparqlQuery);
  }
  
  @GET 
  @Produces({MediaType.APPLICATION_JSON})
  @Path("isValidLoginCredentials")
  public User isValidLoginCredentials(@DefaultValue("") @QueryParam("email") String email,
                                      @DefaultValue("") @QueryParam("password") String password)
  {
    return new User();
  }
}
