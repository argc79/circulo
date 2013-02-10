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
import circulo.circulo_model.Note;

@Path("notes")
public class NoteResource extends Resource<Note> {

	@Override
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Note> findAll() {
		try {
			return controller.getNoteController().findAll();
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
	public Note create(Note t) {
		try {
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
	public Note update(Note t) {
		try {
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
