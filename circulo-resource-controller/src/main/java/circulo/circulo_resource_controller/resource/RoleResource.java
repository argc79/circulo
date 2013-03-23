package circulo.circulo_resource_controller.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.SecurityContext;

import circulo.circulo_controller.ServiceException;
import circulo.circulo_model.Role;

@Path("roles")
public class RoleResource extends Resource implements ResourceInterface<Role> {

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response findAll(@Context SecurityContext sec,
			@Context Request request) {
		try {

			List<Role> roles = controller.getRoleController().findAll();
			EntityTag tag = new EntityTag(Integer.toString(roles.hashCode()));
			CacheControl cc = getCache(1000);
			ResponseBuilder builder = request.evaluatePreconditions(tag);
			if (builder != null) {
				builder.cacheControl(cc);
				return builder.build();
			}
			return getResponseOk(roles, tag, cc);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return null;
	}

	@GET
	@Path("search/{query}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Role> findByName(@PathParam("query") String query) {
		return null;
	}

	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Role findById(@PathParam("id") String id) {
		try {
			System.out.println("el valor de primary key es=" + id);
			return controller.getRoleController().findByPrimaryKey(
					new Integer(id));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return null;
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Role create(@Context SecurityContext sec, Role t) {
		try {
			controller.getRoleController().create(t);
			return t;
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return null;
	}

	@PUT
	@Path("{id}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Role update(@Context SecurityContext sec, Role t) {
		try {
			controller.getRoleController().update(t);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return t;
	}

	@DELETE
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void remove(@PathParam("id") int id) {
		Role role;
		try {
			role = controller.getRoleController().findByPrimaryKey(id);
			controller.getRoleController().remove(role);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

}
