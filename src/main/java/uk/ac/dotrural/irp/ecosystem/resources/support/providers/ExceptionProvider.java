/*
 * rass - Copyright (C) 2010 Geeth de Mel http://www.csd.abdn.ac.uk/~gdemel
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package uk.ac.dotrural.irp.ecosystem.resources.support.providers;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Component;

import uk.ac.dotrural.irp.ecosystem.resources.support.reporters.ExceptionReporter;

@Component
@Provider
public class ExceptionProvider implements ExceptionMapper<ExceptionReporter>
{
  public Response toResponse(ExceptionReporter er)
  {
    StringWriter sw = new StringWriter();
    er.printStackTrace(new PrintWriter(sw));

    String jsonException = "{\"exception\": {\"cause:\":" + er.getLocalizedMessage() + ", \"details\": \""+ sw + "\"}";
    
    return Response.status(Status.NOT_ACCEPTABLE).type(MediaType.APPLICATION_JSON).entity(jsonException).build();
  }
}
