package circulo.circulo_security.userdetails;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import circulo.circulo_model.Person;

public final class CirculoUserDetails extends Person implements UserDetails {
	private static final long serialVersionUID = 4531685431681835196L;

	public CirculoUserDetails(Person user) {
		setId(user.getId());
		setUserName(user.getUserName());
		setEmail(user.getEmail());
		setFirstName(user.getFirstName());
		setLastName(user.getLastName());
		setPassword(user.getPassword());
	}

	public Collection<GrantedAuthority> getAuthorities() {
		return CirculoUserAuthorityUtils.createAuthorities(this);
	}

	public String getUsername() {
		return getUserName();
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		return true;
	}

}
