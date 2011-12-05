package uk.ac.dotrural.irp.restful.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.openjena.fuseki.http.UpdateRemote;

import uk.ac.dotrural.irp.Constants;
import uk.ac.dotrural.irp.restful.models.jaxb.input.Query;
import uk.ac.dotrural.irp.restful.models.jaxb.input.ServiceInitialiser;
import uk.ac.dotrural.irp.restful.models.jaxb.output.SystemMessage;
import uk.ac.dotrural.irp.restful.resources.support.reporters.ExceptionReporter;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.sparql.resultset.JSONOutput;
import com.hp.hpl.jena.update.UpdateRequest;

public abstract class RESTFulSPARQL 
{
    private static String sparql_framework;
    private static String serviceURI;
    private static String updateURI;
    private static String queryURI;
    
    @Context 
    private UriInfo uriInfo;
    
    public RESTFulSPARQL()
    {
      System.setProperty("http.proxyHost", "proxy.abdn.ac.uk");
      System.setProperty("http.proxyPort", "8080");
      System.setProperty("http.nonProxyHosts", "localhost|127.0.0.1");
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @Path("init")
    public SystemMessage init(ServiceInitialiser si)
    {      
      if(si == null)
        throw new ExceptionReporter(new NullPointerException("No initialising parameters given."));
      
      if(si.getUri() != null && !si.getUri().trim().equals(""))
      {
        serviceURI = si.getUri().trim();

        sparql_framework = (si.getSparqlFramework() != null && !si.getSparqlFramework().trim().equals(""))?si.getSparqlFramework():"joseki";
        if(sparql_framework.equals(Constants.FUSEKI))   
        {
          updateURI = serviceURI + "/update";
          queryURI = serviceURI + "/query";
        }
        else
          queryURI = serviceURI;          
        
        System.out.format("  INFO: The sevice URI is '%s'\n", serviceURI);
        System.out.format("  INFO: The query URI is '%s'\n", queryURI);
        if(sparql_framework.equals(Constants.FUSEKI))
          System.out.format("  INFO: The update URI is '%s'\n", updateURI);
        
        return new SystemMessage("success",String.format("Service is created with SPARQL endpoint '%s'",serviceURI));
      }
      else if(si.getServicename() != null && !si.getServicename().trim().equals(""))
      {
        sparql_framework = (si.getSparqlFramework() != null && !si.getSparqlFramework().trim().equals(""))?si.getSparqlFramework():"joseki";
        
        String [] elems = uriInfo.getBaseUri().toString().split("/");
        if(elems.length>=3)
        {
          serviceURI = elems[0] + "//" + elems[2] + (sparql_framework.equals(Constants.JOSEKI)?"/joseki/":"/") + si.getServicename();
          if(sparql_framework.equals(Constants.FUSEKI))   
          {
            updateURI = serviceURI + "/update";
            queryURI = serviceURI + "/query";
          }
          else
            queryURI = serviceURI;          
          
          System.out.format("  INFO: The sevice URI is '%s'\n", serviceURI);
          System.out.format("  INFO: The query URI is '%s'\n", queryURI);
          if(sparql_framework.equals(Constants.FUSEKI))
            System.out.format("  INFO: The update URI is '%s'\n", updateURI);
          
          return new SystemMessage("success",String.format("Service is created with SPARQL endpoint '%s'",serviceURI));
        }
        else
          throw new ExceptionReporter(new NullPointerException(String.format("Peculiar base URI '%s'",uriInfo.getBaseUri().toString())));
      }
      
      throw new ExceptionReporter(new NullPointerException(RESTFulSPARQL.class.getSimpleName() + 
                                                           " -> SPARQL endpoint is not given."));
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @Path("update")
    public void update(Query query)
    {
      if(sparql_framework.equals(Constants.JOSEKI))
        throw new ExceptionReporter(new Exception(String.format("This SPARQL endpoint %s is based on Joseki.\n" +
                                                    		        "It does not support SPARQL updates such as INSERT/DELETE/UPDATE/",
                                                    		        serviceURI)
                                                 )
                                   );
      
      if(serviceURI == null)
        throw new ExceptionReporter(new NullPointerException("Service MUST be initialised with a SPARQL endpoint."));
      if(query == null)
        throw new ExceptionReporter(new NullPointerException("No SPARQL query given."));
      if(query.getQuery() == null)
        throw new ExceptionReporter(new NullPointerException("SPARQL query is not given."));
      if(query.getQuery().equals(""))
        throw new ExceptionReporter(new NullPointerException("Empty SPARQL query."));
        
      String sQuery =  query.getQuery();
        
      System.out.format("  INFO: %s => %s\n", sQuery,updateURI);
        
      UpdateRequest ur = new UpdateRequest();
      ur.add(sQuery);
      UpdateRemote.execute(ur, updateURI);
    }
    
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @Path("query")
    public String query(Query query) 
    {
      if(serviceURI == null)
        throw new ExceptionReporter(new NullPointerException("Service MUST be initialised with a SPARQL endpoint."));
      if(query == null)
        throw new ExceptionReporter(new NullPointerException("No SPARQL query given."));
      if(query.getQuery() == null)
        throw new ExceptionReporter(new NullPointerException("SPARQL query is not given."));
      if(query.getQuery().equals(""))
        throw new ExceptionReporter(new NullPointerException("Empty SPARQL query."));
        
      String sQuery =  query.getQuery();
        
      System.out.format("  INFO: %s => %s\n", sQuery,queryURI);
        
      QueryExecution queryExecution = QueryExecutionFactory.sparqlService(queryURI, sQuery);
      ResultSet results = queryExecution.execSelect();
      JSONOutput json = new JSONOutput();
      String jsonResults = json.asString(results);
  
      return jsonResults;
    }
}