package circulo.circulo_controller;

import java.util.List;

import javax.persistence.Query;

import circulo.circulo_model.Role;

public final class RoleController extends BaseService<Integer, Role> {

	@Override
	public Integer create(Role arg) throws ServiceException {
		persist(arg);
		return arg.getId();
	}

	@Override
	public Integer remove(Role arg) throws ServiceException {
		delete(arg);
		return arg.getId();
	}

	@Override
	public Integer update(Role arg) throws ServiceException {
		merge(arg);
		return arg.getId();
	}

	@Override
	public Role findByPrimaryKey(Integer pk) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> findAll() throws ServiceException {
		Query query = getQuery("findAllRoles");
		return query.getResultList();
	}

}
