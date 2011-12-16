package uk.ac.dotrural.irp.ecosystem.services;

import javax.ws.rs.core.UriInfo;

import org.openjena.fuseki.http.UpdateRemote;

import uk.ac.dotrural.irp.ecosystem.Constants;
import uk.ac.dotrural.irp.ecosystem.models.jaxb.system.EndpointInfo;
import uk.ac.dotrural.irp.ecosystem.models.jaxb.system.Query;
import uk.ac.dotrural.irp.ecosystem.models.jaxb.system.ServiceInitialiser;
import uk.ac.dotrural.irp.ecosystem.models.jaxb.system.SystemMessage;
import uk.ac.dotrural.irp.ecosystem.resources.support.reporters.ExceptionReporter;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.update.UpdateRequest;

public class SPARQLEndpoint
{
    private String sparql_framework;
    private String serviceURI;
    private String updateURI;
    private String queryURI;
    private UriInfo uriInfo;
        
    public SPARQLEndpoint()
    {}

    public String getSparql_framework()
    {
      return sparql_framework;
    }

    public void setSparql_framework(String sparql_framework)
    {
      this.sparql_framework = sparql_framework;
    }

    public String getServiceURI()
    {
      return serviceURI;
    }

    public void setServiceURI(String serviceURI)
    {
      this.serviceURI = serviceURI;
    }

    public String getUpdateURI()
    {
      return updateURI;
    }

    public void setUpdateURI(String updateURI)
    {
      this.updateURI = updateURI;
    }

    public String getQueryURI()
    {
      return queryURI;
    }

    public void setQueryURI(String queryURI)
    {
      this.queryURI = queryURI;
    }

    public UriInfo getUriInfo()
    {
      return uriInfo;
    }

    public void setUriInfo(UriInfo uriInfo)
    {
      this.uriInfo = uriInfo;
    }

    public SystemMessage init(UriInfo uriInfo, ServiceInitialiser si)
    {      
      if(uriInfo == null)
        throw new ExceptionReporter(new NullPointerException("Initialising error. Context 'UriInfo' missing."));
      
      this.uriInfo = uriInfo;
      
      
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
      
      throw new ExceptionReporter(new NullPointerException(SPARQLEndpoint.class.getSimpleName() + 
                                                           " -> SPARQL endpoint is not given."));
    }

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
    
    public ResultSet query(Query query) 
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
  
      return results;
    }
    
    public boolean ask(Query query)
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
      return queryExecution.execAsk();
    }
    
    public EndpointInfo info()
    {
      EndpointInfo endpointInfo = new EndpointInfo();
      endpointInfo.setServiceURI(this.getServiceURI());
      endpointInfo.setSparql_framework(this.getSparql_framework());
      endpointInfo.setQueryURI(this.getQueryURI());
      endpointInfo.setUpdateURI(this.getUpdateURI());
      
      return endpointInfo;
    }
}