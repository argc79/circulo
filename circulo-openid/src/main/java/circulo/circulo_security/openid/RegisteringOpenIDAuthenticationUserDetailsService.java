package circulo.circulo_security.openid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAttribute;
import org.springframework.security.openid.OpenIDAuthenticationToken;

import circulo.circulo_model.Person;
import circulo.circulo_security.service.CirculoService;

//@Component
public final class RegisteringOpenIDAuthenticationUserDetailsService implements
		AuthenticationUserDetailsService<OpenIDAuthenticationToken> {
	private UserDetailsService userDetailsService;
	private CirculoService circuloService;

	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	public void setCirculoService(CirculoService circuloService) {
		this.circuloService = circuloService;
	}

	// @Autowired
	// public RegisteringOpenIDAuthenticationUserDetailsService(
	// UserDetailsService userDetailsService, CirculoService circuloService) {
	// if (userDetailsService == null) {
	// throw new IllegalArgumentException(
	// "userDetailsService cannot be null");
	// }
	// if (circuloService == null) {
	// throw new IllegalArgumentException("controller cannot be null");
	// }
	//
	// this.userDetailsService = userDetailsService;
	// this.circuloService = circuloService;
	// }

	public UserDetails loadUserDetails(OpenIDAuthenticationToken token)
			throws UsernameNotFoundException {
		String openid = token.getIdentityUrl();
		// try {
		Person user = circuloService.findUserByOpenId(openid);
		if (user == null) {
			List<OpenIDAttribute> attrs = token.getAttributes();
			Person newUser = new Person();
			newUser.setUserName(openid);
			newUser.setEmail(getAttr("email", attrs));
			newUser.setFirstName(getFirstName(attrs));
			newUser.setLastName(getLastName(attrs));
			newUser.setPassword("notused");
			if (circuloService.createUser(newUser) == -1) {
				throw new UsernameNotFoundException(
						"The user could not be created");
			}
		}
		return userDetailsService.loadUserByUsername(openid);
	}

	private String getFirstName(List<OpenIDAttribute> attrs) {
		String firstName = getAttr("firstname", attrs);
		if (firstName != null) {
			return firstName;
		}
		return parseFullName(attrs, true);
	}

	private String getLastName(List<OpenIDAttribute> attrs) {
		String firstName = getAttr("lastname", attrs);
		if (firstName != null) {
			return firstName;
		}
		return parseFullName(attrs, false);
	}

	private String parseFullName(List<OpenIDAttribute> attrs,
			boolean isFirstName) {
		String fullName = getAttr("fullname", attrs);
		if (fullName == null) {
			return null;
		}
		int part = isFirstName ? 0 : 1;
		return fullName.split(" ", 2)[part];
	}

	private String getAttr(String attrName, List<OpenIDAttribute> attrs) {
		List<String> attrValues = getAttrs(attrName, attrs);
		if (attrValues.isEmpty()) {
			return null;
		}
		if (attrValues.size() != 1) {
			throw new IllegalArgumentException(
					"Could not obtain a single value for attribute of name "
							+ attrName + "from the attributes. Got " + attrs);
		}
		return attrValues.iterator().next();
	}

	private List<String> getAttrs(String attrName, List<OpenIDAttribute> attrs) {
		for (OpenIDAttribute attr : attrs) {
			if (attrName.equals(attr.getName())) {
				return new ArrayList<String>(attr.getValues());
			}
		}
		return Collections.emptyList();
	}
}
