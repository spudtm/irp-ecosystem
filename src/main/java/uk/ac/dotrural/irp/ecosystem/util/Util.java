package uk.ac.dotrural.irp.ecosystem.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import uk.ac.dotrural.irp.ecosystem.models.jaxb.timetable.Line;
import uk.ac.dotrural.irp.ecosystem.queries.alert.ServiceDays;

import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.sparql.resultset.JSONOutput;

public class Util
{
  public static String resultsetToString(ResultSet results)
  {
    JSONOutput json = new JSONOutput();
    String jsonResults = json.asString(results);

    return jsonResults;
  }

  /**
   * Creates a string consisting of params.length repetitions of sparqlQuery,
   * each with one value of param inserted
   * 
   * @param sparqlQuery
   *            SPARQL query with one %s to be replaced
   * @param params
   *            looped through to replace the %s in sparqlQuery
   * @return A string containing params occurances of sparqlQuery, each with a
   *         different param inserted for the %s in sparqlQuery
   */
  public static String buildRepetativeQuery(String sparqlQuery, String... params) {
    StringBuilder sparql = new StringBuilder();
    for (String param : params) {
      sparql.append(String.format(sparqlQuery, param));
    }
    return sparql.toString();
  }

  public static String getNodeValue(RDFNode rdfNode) 
  {
    if (rdfNode == null)
      return "";
    else if (rdfNode.isLiteral())
      return rdfNode.asLiteral().getLexicalForm();
    else if (rdfNode.isResource() || rdfNode.isURIResource())
      return rdfNode.asResource().getURI();
    else 
      return rdfNode.toString();
  }
  
  public static Line contains(List<Line> lines, String lineUri)
  {
    if(lines.size() == 0)
      return null;
    
    for(Line route:lines)
    {
      if(route.getUri().equals(lineUri.trim()))
        return route;
    }
    
    return null;
  }
  
  public static String getMD5(String str) throws NoSuchAlgorithmException
  {
    MessageDigest algorithm = MessageDigest.getInstance("MD5");
    algorithm.reset();
    algorithm.update(str.getBytes());
    
    StringBuffer hexString = new StringBuffer();
    for (byte b:algorithm.digest())
      hexString.append(Integer.toHexString(0xFF & b));
    
    return hexString.toString();
  }
  
  public static ServiceDays [] getServiceDays(boolean... days)
  {
    ArrayList<ServiceDays> selectedDays = new ArrayList<ServiceDays>();
    ServiceDays [] serviceDays = ServiceDays.values();
    
    for(int i=0;i<days.length;i++)
    {
      if(days[i])
        selectedDays.add(serviceDays[i]);
    }
    return selectedDays.toArray(new ServiceDays[selectedDays.size()]);
  }
}
