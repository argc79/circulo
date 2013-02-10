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
import circulo.circulo_model.Tag;

@Path("tags")
public class TagResource extends Resource<Tag> {

	@Override
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Tag> findAll() {
		try {
			return controller.getTagController().findAll();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@GET
	@Path("search/{query}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Tag> findByName(@PathParam("query") String query) {
		return null;
	}

	@Override
	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Tag findById(@PathParam("id") String id) {
		try {
			System.out.println("el valor de primary key es=" + id);
			return controller.getTagController().findByPrimaryKey(
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
	public Tag create(Tag t) {
		try {
			controller.getTagController().create(t);
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
	public Tag update(Tag t) {
		try {
			controller.getTagController().update(t);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return t;
	}

	@Override
	@DELETE
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void remove(int id) {
		Tag tag;
		try {
			tag = controller.getTagController().findByPrimaryKey(id);
			controller.getTagController().remove(tag);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

}