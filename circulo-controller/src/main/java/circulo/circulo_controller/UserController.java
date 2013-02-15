package circulo.circulo_controller;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import circulo.circulo_model.Person;

public class UserController extends BaseService<Integer, Person> {
	public boolean login(Person user) {
		Query query = getQuery("findByUserName");
		query.setParameter("userName", user.getUserName());
		try {
			Person singleResult = (Person) query.getSingleResult();
			return (singleResult != null && singleResult.getPassword().equals(
					user.getPassword())) ? true : false;
		} catch (NoResultException nre) {
			return false;
		}
	}

	@Override
	public Integer create(Person arg) throws ServiceException {
		persist(arg);
		return arg.getId();
	}

	@Override
	public Integer remove(Person arg) throws ServiceException {
		delete(arg);
		return arg.getId();
	}

	@Override
	public Integer update(Person arg) throws ServiceException {
		merge(arg);
		return arg.getId();
	}

	@Override
	public Person findByPrimaryKey(Integer pk) throws ServiceException {
		return getEntityManager().find(Person.class, pk);
	}

	public Person findByName(String name) throws ServiceException {
		final Query query = getQuery("findByUserName");
		query.setParameter("userName", name);
		return (Person) query.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Person> findAll() throws ServiceException {
		Query query = getQuery("findAllUsers");
		return query.getResultList();
	}
}
