package uk.ac.dotrural.irp.restful.resources.ext;

import javax.ws.rs.Path;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import uk.ac.dotrural.irp.restful.models.jaxb.input.Query;
import uk.ac.dotrural.irp.restful.models.jaxb.input.ServiceInitialiser;
import uk.ac.dotrural.irp.restful.models.jaxb.output.SystemMessage;
import uk.ac.dotrural.irp.restful.resources.RESTFulSPARQL;

@Path("/routes")
@Component
@Scope("request")
public class Routes extends RESTFulSPARQL
{
  @Override
  public SystemMessage init(ServiceInitialiser si)
  {
    return super.init(si);
  }
  
  @Override
  public void update(Query query)
  {
    super.update(query);
  }

  @Override
  public String query(Query query) 
  {
    return super.query(query);
  }
}
