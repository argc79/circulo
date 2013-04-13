package circulo.circulo_controller;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.Query;

import circulo.circulo_model.Tag;

public final class TagController extends BaseService<Integer, Tag> {
	private final Logger logger = Logger.getLogger("circulo");

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

	public List<?> findTagsList(String userName) throws ServiceException {
		String query = String
				.format("select tag.id, tag.name from tag, person where tag.person_id = person.id and person.username = '%s'",
						userName);
		logger.info(query);
		Query result = getEntityManager().createNativeQuery(query);
		return result.getResultList();
	}

	public List<?> findTagsList(String userName, String query)
			throws ServiceException {
		String q = String
				.format("select tag.id, tag.name from tag, person where tag.person_id = person.id and person.username = '%s' and tag.name ilike '%%%s%%'",
						userName, query);
		logger.info(q);
		Query result = getEntityManager().createNativeQuery(q);
		return result.getResultList();
	}

}
