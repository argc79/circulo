package circulo.circulo_security.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

public class AuthenticationOkHandler extends
		SimpleUrlAuthenticationSuccessHandler {

	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		// final SavedRequest requestCache = new HttpSessionRequestCache()
		// .getRequest(request, response);
		// setDefaultTargetUrl("/#tags/90");
		// String redirectUrl = requestCache.getRedirectUrl();
		// arg0.getHeaderNames()
		super.onAuthenticationSuccess(request, response, authentication);
	}

}
