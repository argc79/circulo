package circulo.circulo_security.service;

import circulo.circulo_controller.Controller;
import circulo.circulo_controller.ServiceException;
import circulo.circulo_model.Person;

public class DefaultCirculoService implements CirculoService {

	private Controller controller;

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public Person getUser(int id) {
		try {
			return controller.getUserController().findByPrimaryKey(id);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Person findUserByOpenId(String openid) {
		try {
			return controller.getUserController().findByName(openid);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int createUser(Person user) {
		try {
			return controller.getUserController().create(user);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return -1;
	}

}
