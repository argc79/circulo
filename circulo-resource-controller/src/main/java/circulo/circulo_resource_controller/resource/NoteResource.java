package circulo.circulo_resource_controller.resource;

import java.util.ArrayList;
import java.util.Date;
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
import circulo.circulo_model.Note;

@Path("notes")
public class NoteResource extends Resource<Note> {

	@Override
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response findAll(@Context SecurityContext sec,
			@Context Request request) {
		try {
			List<?> results = controller.getNoteController().findNotesList(
					sec.getUserPrincipal().getName());
			List<Note> notes = new ArrayList<Note>();
			for (int i = 0; i < results.size(); i++) {
				Object[] line = (Object[]) results.get(i);
				Note note = new Note();
				note.setId((Integer) line[0]);
				note.setSubject((String) line[1]);
				note.setCreatedOn((Date) line[2]);
				note.setModifiedOn((Date) line[3]);
				notes.add(note);
			}

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

	@Override
	@GET
	@Path("search/{query}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Note> findByName(@PathParam("query") String query) {
		return null;
	}

	@Override
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

	@Override
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Note create(@Context SecurityContext sec, Note t) {
		try {
			t.setPerson(controller.getUserController().findByName(
					sec.getUserPrincipal().getName()));
			controller.getNoteController().create(t);
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
	public Note update(@Context SecurityContext sec, Note t) {
		try {
			t.setPerson(controller.getUserController().findByName(
					sec.getUserPrincipal().getName()));
			controller.getNoteController().update(t);
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
		Note note;
		try {
			note = controller.getNoteController().findByPrimaryKey(id);
			controller.getNoteController().remove(note);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
}
