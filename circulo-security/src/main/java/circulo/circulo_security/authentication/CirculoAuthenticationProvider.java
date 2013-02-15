package circulo.circulo_security.authentication;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import circulo.circulo_controller.Controller;
import circulo.circulo_model.Person;

public class CirculoAuthenticationProvider implements AuthenticationProvider {
	private Controller controller;

	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;
		String username = String.valueOf(auth.getPrincipal());
		String password = String.valueOf(auth.getCredentials());

		System.out.println("loggin on username:" + username);

		// 1. Use the username to load the data for the user, including
		// authorities and password.
		Person user = new Person();
		user.setUserName(username);
		user.setPassword(password);
		// 2. Check the passwords match.
		if (!controller.getUserController().login(user)) {
			throw new BadCredentialsException("Bad Credentials");
		}

		user.setPassword("");
		auth.setAuthenticated(true);

		return auth;
	}

	@Override
	public boolean supports(Class<? extends Object> authentication) {
		return (UsernamePasswordAuthenticationToken.class
				.isAssignableFrom(authentication));
	}

	protected void setController(Controller controller) {
		this.controller = controller;
	}
}
