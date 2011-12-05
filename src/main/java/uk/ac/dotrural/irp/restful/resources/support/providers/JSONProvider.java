package uk.ac.dotrural.irp.restful.resources.support.providers;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;

import org.springframework.stereotype.Component;

import uk.ac.dotrural.irp.restful.models.jaxb.input.Query;
import uk.ac.dotrural.irp.restful.models.jaxb.input.ServiceInitialiser;

import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.api.json.JSONJAXBContext;

@Component
@Provider
public class JSONProvider implements ContextResolver<JAXBContext>
{
  private JAXBContext context;
  private Class<?>[] types = {ServiceInitialiser.class,Query.class};
  
  public JSONProvider() throws Exception
  {
    this.context = new JSONJAXBContext(
                                        JSONConfiguration.natural().build(),
                                        types
                                      );
  }
  
  public JAXBContext getContext(Class<?> objectType)
  {
    for(Class<?> type:types)
    {
      if(type.equals(objectType))
        return context;
    }
    
    return null;
  }
}
