package circulo.circulo_controller;

import java.util.List;

import javax.persistence.Query;

import circulo.circulo_model.Tag;

public final class TagController extends BaseService<Integer, Tag> {

	@Override
	public Integer create(Tag arg) throws ServiceException {
		persist(arg);
		return arg.getId();
	}

	@Override
	public Integer remove(Tag arg) throws ServiceException {
		delete(arg);
		return arg.getId();
	}

	@Override
	public Integer update(Tag arg) throws ServiceException {
		merge(arg);
		return arg.getId();
	}

	@Override
	public Tag findByPrimaryKey(Integer pk) throws ServiceException {
		return getEntityManager().find(Tag.class, pk);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tag> findAll() throws ServiceException {
		Query query = getQuery("findAllTags");

		return query.getResultList();
	}

}
