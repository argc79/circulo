package circulo.circulo_security.service;

import circulo.circulo_model.Person;

public interface CirculoService {

	Person getUser(int id);

	Person findUserByOpenId(String openid);

	int createUser(Person user);

}
