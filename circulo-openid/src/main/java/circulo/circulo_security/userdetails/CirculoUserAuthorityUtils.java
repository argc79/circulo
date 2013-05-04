package circulo.circulo_security.userdetails;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import circulo.circulo_model.Person;

public final class CirculoUserAuthorityUtils {
	private static final List<GrantedAuthority> ADMIN_ROLES = AuthorityUtils
			.createAuthorityList("ROLE_ADMIN", "ROLE_USER");
	private static final List<GrantedAuthority> USER_ROLES = AuthorityUtils
			.createAuthorityList("ROLE_USER");

	public static Collection<GrantedAuthority> createAuthorities(Person user) {
		String username = user.getUserName();
		if (username.startsWith("admin")) {// TODO improve this
			return ADMIN_ROLES;
		}
		return USER_ROLES;
	}

	private CirculoUserAuthorityUtils() {
	}
}
