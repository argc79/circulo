package circulo.circulo_controller;

import java.util.List;

import javax.persistence.Query;

import circulo.circulo_model.Note;

public final class NoteController extends BaseService<Integer, Note> {

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
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Note> findAll() throws ServiceException {
		Query query = getQuery("findAllNotes");
		return query.getResultList();
	}

}
