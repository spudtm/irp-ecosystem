package uk.ac.dotrural.irp.restful.test;

import org.openjena.fuseki.http.UpdateRemote;

import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.update.UpdateRequest;


public class Test
{
  public static void main(String[] args) 
  {
    System.setProperty("http.proxyHost", "proxy.abdn.ac.uk");
    System.setProperty("http.proxyPort", "8080");
    System.setProperty("http.nonProxyHosts", "localhost|127.0.0.1");

    String dc = "http://purl.org/dc/elements/1.1/";
    String vcard = "http://www.w3.org/2001/vcard-rdf/3.0#";
    String ns = "http://example.org/ns";
    
    UpdateRequest ur = new UpdateRequest();
//    ur.setPrefix("dc", dc);
//    ur.setPrefix("vcard", vcard);
//    ur.setPrefix("ns", ns);
    
    if(args.length == 1)
    {
      if(!args[0].equals("delete"))
      {
        ur.add("INSERT DATA " +
            "{ <http://example.org/book/book8> <http://purl.org/dc/elements/1.1/title> \"Lifeless\" }"
           );
        UpdateRemote.execute(ur, "http://localhost:3030/books/update");
      }
      else
      {
        ur.add("DELETE DATA " +
            "{ <http://example.org/book/book8> dc:title \"Lifeless\" }"
           );
        UpdateRemote.execute(ur, "http://dtp-24.sncs.abdn.ac.uk:8090/dataset/update");
      }
    }
    String query= "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n" +
                  "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n" +
                  "SELECT * WHERE {?s ?p ?o}";

    ResultSet results= QueryExecutionFactory.sparqlService("http://localhost:3030/books/query", query).execSelect();
    ResultSetFormatter.outputAsJSON(results);  
    
//    String uriService = "http://dtp-24.sncs.abdn.ac.uk:8085/locations/query";
//    String query = "select ?long ?lat ?east ?north where {" + 
//                   "  ?x a <http://localhost/irp/locations/LocationObservationValue>." +
//                   "  ?x <http://www.w3.org/2003/01/geo/wgs84_pos#lat> ?lat." +
//                   "  ?x <http://www.w3.org/2003/01/geo/wgs84_pos#long> ?long." +
//                   "  ?x <http://data.ordnancesurvey.co.uk/ontology/spatialrelations/easting> ?east." +
//                   "  ?x <http://data.ordnancesurvey.co.uk/ontology/spatialrelations/northing> ?north." +
//                   "}"; 
//    ResultSet results= QueryExecutionFactory.sparqlService(uriService, query).execSelect();
//    ResultSetFormatter.outputAsJSON(results);
//    
//    uriService = "http://dtp-24.sncs.abdn.ac.uk:8086/operators/query";
//    query = "select ?noc ?opn where {" +
//            "  ?opt a <http://localhost/irp/travelOperator/TravelOperator>." +
//            "  ?opt <http://localhost/irp/travelOperator/nationalOperatorCode> ?noc." +
//            "  ?opt <http://localhost/irp/travelOperator/operatorPublicName> ?opn." +
//            "  ?opt <http://localhost/irp/travelOperator/operatorTravelMode> <http://localhost/irp/travelOperator/TravelMode/Airline>." +
//            "} ORDER BY ?noc";
//    results= QueryExecutionFactory.sparqlService(uriService, query).execSelect();
//    ResultSetFormatter.outputAsJSON(results);
//
//    uriService = "http://dtp-24.sncs.abdn.ac.uk:8087/routes/query";
//    query = "select ?linkid where {" +
//            "  ?s a <http://localhost/irp/map/RouteMap>." +
//            "  ?s <http://localhost/irp/map/links> ?link." +
//            "  ?link <http://localhost/irp/map/linkNumber> ?linkid." +
//            "} ORDER BY ?linkid";
//    results= QueryExecutionFactory.sparqlService(uriService, query).execSelect();
//    ResultSetFormatter.outputAsJSON(results);
  }
}
