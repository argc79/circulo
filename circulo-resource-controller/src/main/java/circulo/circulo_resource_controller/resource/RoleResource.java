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
import javax.ws.rs.core.MediaType;

import circulo.circulo_controller.ServiceException;
import circulo.circulo_model.Role;

@Path("roles")
public class RoleResource extends Resource<Role> {

	@Override
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Role> findAll() {
		try {
			return controller.getRoleController().findAll();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@GET
	@Path("search/{query}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Role> findByName(@PathParam("query") String query) {
		return null;
	}

	@Override
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

	@Override
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Role create(Role t) {
		try {
			controller.getRoleController().create(t);
			return t;
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@PUT
	@Path("{id}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Role update(Role t) {
		try {
			controller.getRoleController().update(t);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return t;
	}

	@Override
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
