package circulo.circulo_controller;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.Query;

import circulo.circulo_model.Note;

public final class NoteController extends BaseService<Integer, Note> {
	private final Logger logger = Logger.getLogger("circulo");

	@Override
	public Integer create(Note arg) throws ServiceException {
		persist(arg);
		return arg.getId();
	}

	@Override
	public Integer remove(Note arg) throws ServiceException {
		delete(arg);
		return arg.getId();
	}

	@Override
	public Integer update(Note arg) throws ServiceException {
		merge(arg);
		return arg.getId();
	}

	@Override
	public Note findByPrimaryKey(Integer pk) throws ServiceException {
		return getEntityManager().find(Note.class, pk);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Note> findAll() throws ServiceException {
		Query query = getQuery("findAllNotes");
		return query.getResultList();
	}

	public List<?> findNotesList(String userName) throws ServiceException {
		String query = String
				.format("select note.id, note.subject, note.createdOn, note.modifiedOn from note, person where note.person_id = person.id and person.username = '%s'",
						userName);
		logger.info(query);
		Query result = getEntityManager().createNativeQuery(query);
		return result.getResultList();
	}

	public List<?> findNotesList(String userName, String query)
			throws ServiceException {
		String q = String
				.format("select note.id, note.subject, note.createdOn, note.modifiedOn from note, person where note.person_id = person.id and person.username = '%s' and note.subject like '%%%s%%'",
						userName, query);
		logger.info(q);
		Query result = getEntityManager().createNativeQuery(q);
		return result.getResultList();
	}
}
