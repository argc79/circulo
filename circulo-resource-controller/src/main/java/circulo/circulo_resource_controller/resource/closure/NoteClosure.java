package circulo.circulo_resource_controller.resource.closure;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.Closure;

import circulo.circulo_model.Note;

public class NoteClosure implements Closure {
	private final List<Note> notes;

	public NoteClosure(List<Note> notes) {
		this.notes = notes;
	}

	public void execute(Object arg0) {
		Object[] line = (Object[]) arg0;
		Note note = new Note();
		note.setId((Integer) line[0]);
		note.setSubject((String) line[1]);
		note.setCreatedOn((Date) line[2]);
		note.setModifiedOn((Date) line[3]);
		notes.add(note);
	}
}
