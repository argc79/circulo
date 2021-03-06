package circulo.circulo_resource_controller.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
import circulo.circulo_resource_controller.resource.closure.CreateUndefinedTagClosure;
import circulo.circulo_resource_controller.resource.closure.NoteClosure;

@Path("notes")
public class NoteResource extends Resource implements ResourceInterface<Note> {

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response findAll(@Context SecurityContext sec,
			@Context Request request) {
		try {
			List<?> results = controller.getNoteController().findNotesList(
					sec.getUserPrincipal().getName());
			List<Note> notes = new ArrayList<Note>();
			CollectionUtils.forAllDo(results, new NoteClosure(notes));

			EntityTag tag = new EntityTag(Integer.toString(notes.hashCode()));
			CacheControl cc = getCache(1000);
			ResponseBuilder builder = request.evaluatePreconditions(tag);
			if (builder != null) {
				builder.cacheControl(cc);
				return builder.build();
			}
			return getResponseOk(notes, tag, cc);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return null;
	}

	@GET
	@Path("search/{query}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Note> findByName(@PathParam("query") String query) {
		return null;
	}

	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Note findById(@PathParam("id") String id) {
		try {
			return controller.getNoteController().findByPrimaryKey(
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
	public Note create(@Context SecurityContext sec, Note t) {
		try {
			final String userName = sec.getUserPrincipal().getName();
			Set<Tag> tags = t.getTags();
			CollectionUtils.forAllDo(tags, new CreateUndefinedTagClosure(
					controller, userName));

			t.setPerson(controller.getUserController().findByName(userName));
			controller.getNoteController().create(t);
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
	public Note update(@Context SecurityContext sec, Note t) {
		try {
			final String userName = sec.getUserPrincipal().getName();
			Set<Tag> tags = t.getTags();
			CollectionUtils.forAllDo(tags, new CreateUndefinedTagClosure(
					controller, userName));
			t.setPerson(controller.getUserController().findByName(
					sec.getUserPrincipal().getName()));
			controller.getNoteController().update(t);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return t;
	}

	@DELETE
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void remove(@PathParam("id") int id) {
		Note note;
		try {
			note = controller.getNoteController().findByPrimaryKey(id);
			controller.getNoteController().remove(note);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
}
