package circulo.circulo_resource_controller.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

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

import org.apache.commons.collections.CollectionUtils;

import circulo.circulo_controller.ServiceException;
import circulo.circulo_model.Note;
import circulo.circulo_model.Tag;
import circulo.circulo_resource_controller.resource.closure.TagClosure;

@Path("tags")
public class TagResource extends Resource<Tag> {
	private final Logger logger = Logger.getLogger("circulo");

	@Override
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response findAll(@Context SecurityContext sec,
			@Context Request request) {

		try {
			List<?> results = controller.getTagController().findTagsList(
					sec.getUserPrincipal().getName());
			List<Tag> tags = new ArrayList<Tag>();
			for (int i = 0; i < results.size(); i++) {
				Object[] line = (Object[]) results.get(i);
				Tag tag = new Tag();
				tag.setId((Integer) line[0]);
				tag.setName((String) line[1]);
				tags.add(tag);
			}

			EntityTag tag = new EntityTag(Integer.toString(tags.hashCode()));
			CacheControl cc = getCache(1000);
			ResponseBuilder builder = request.evaluatePreconditions(tag);
			if (builder != null) {
				builder.cacheControl(cc);
				return builder.build();
			}
			return getResponseOk(tags, tag, cc);
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
	// @GET
	// @Path("{id}")
	// @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Tag findById(String id) {
		// try {
		// System.out.println("el valor de primary key es=" + id);
		// return controller.getTagController().findByPrimaryKey(
		// new Integer(id));
		// } catch (NumberFormatException e) {
		// e.printStackTrace();
		// } catch (ServiceException e) {
		// e.printStackTrace();
		// }
		return null;
	}

	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getNotes(@Context SecurityContext sec,
			@Context Request request, @PathParam("id") String id) {
		try {
			Tag selectedTag = controller.getTagController().findByPrimaryKey(
					new Integer(id));
			final Set<Note> notes = selectedTag.getNotes();
			final List<Note> retvalNotes = new ArrayList<Note>();
			CollectionUtils.forAllDo(notes, new TagClosure(retvalNotes));

			EntityTag tag = new EntityTag(Integer.toString(notes.hashCode()));
			CacheControl cc = getCache(1000);
			ResponseBuilder builder = request.evaluatePreconditions(tag);
			if (builder != null) {
				builder.cacheControl(cc);
				return builder.build();
			}
			return getResponseOk(retvalNotes, tag, cc);

		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return null;
	}

	@Override
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Tag create(@Context SecurityContext sec, Tag t) {
		try {
			t.setPerson(controller.getUserController().findByName(
					sec.getUserPrincipal().getName()));
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
	public Tag update(@Context SecurityContext sec, Tag t) {
		try {
			t.setPerson(controller.getUserController().findByName(
					sec.getUserPrincipal().getName()));
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
	public void remove(@PathParam("id") int id) {
		Tag tag;
		try {
			tag = controller.getTagController().findByPrimaryKey(id);
			controller.getTagController().remove(tag);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

}
