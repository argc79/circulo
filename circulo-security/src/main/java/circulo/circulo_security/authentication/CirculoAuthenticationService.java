package circulo.circulo_security.authentication;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import circulo.circulo_controller.Controller;
import circulo.circulo_controller.ServiceException;
import circulo.circulo_model.Person;

public class CirculoAuthenticationService implements UserDetailsService {

	private Controller controller;

	public void setController(Controller controller) {
		this.controller = controller;
	}

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		final List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>();
		AUTHORITIES.add(new GrantedAuthorityImpl("ROLE_USER"));
		final User invalidUser = new User(username, "-1", false, false, false,
				false, AUTHORITIES);
		final Person person;
		try {
			System.out.println(username);
			person = controller.getUserController().findByName(username);
		} catch (ServiceException e) {
			return invalidUser;
		}
		if (person == null) {
			return invalidUser;
		}
		return new User(person.getUserName(), person.getPassword(), true, true,
				true, true, AUTHORITIES);
	}

}
