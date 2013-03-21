package circulo.circulo_resource_controller.resource.closure;

import java.util.List;

import org.apache.commons.collections.Closure;

import circulo.circulo_model.Note;

public class TagClosure implements Closure {
	private final List<Note> retvalNotes;

	public TagClosure(List<Note> retvalNotes) {
		this.retvalNotes = retvalNotes;
	}

	public void execute(Object n) {
		Note note = (Note) n;
		Note cloned = new Note();
		cloned.setId(note.getId());
		cloned.setCreatedOn(note.getCreatedOn());
		cloned.setModifiedOn(note.getModifiedOn());
		cloned.setSubject(note.getSubject());
		retvalNotes.add(cloned);
	}

}
