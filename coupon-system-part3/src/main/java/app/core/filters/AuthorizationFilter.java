package app.core.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;

import app.core.auth.UserCredentials;

public class AuthorizationFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("=== Authorization filter started");
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		if (httpRequest.getMethod().equalsIgnoreCase("options")) {
			System.out.println("=== PREFLIGHT (Authorization filter)");
			chain.doFilter(request, response);
		} else {
			String requestUri = httpRequest.getRequestURI();
			UserCredentials userCredentials = (UserCredentials) httpRequest.getAttribute("userCredentials");
			System.out.println("=== Authorization filter - request uri: " + requestUri);

			if (requestUri.contains("/api/admin")
					&& userCredentials.getClientType() != UserCredentials.ClientType.ADMIN) {
				httpResponse.setHeader("Access-Control-Allow-Origin", "*");
				httpResponse.setHeader("WWW-Authenticate", "Bearer realm=\"ADMIN API\"");
				httpResponse.setHeader("Access-Control-Expose-Headers", "*");
				httpResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "Only Admin can access this zone!");
			} else if (requestUri.contains("/api/company")
					&& userCredentials.getClientType() != UserCredentials.ClientType.COMPANY) {
				httpResponse.setHeader("Access-Control-Allow-Origin", "*");
				httpResponse.setHeader("WWW-Authenticate", "Bearer realm=\"COMPANY API\"");
				httpResponse.setHeader("Access-Control-Expose-Headers", "*");
				httpResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "Only Company can access this zone!");
			} else if (requestUri.contains("/api/customer")
					&& userCredentials.getClientType() != UserCredentials.ClientType.CUSTOMER) {
				httpResponse.setHeader("Access-Control-Allow-Origin", "*");
				httpResponse.setHeader("WWW-Authenticate", "Bearer realm=\"CUSTOMER API\"");
				httpResponse.setHeader("Access-Control-Expose-Headers", "*");
				httpResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "Only Cusomer can access this zone!");
			} else {
				chain.doFilter(request, response);
			}
		}

	}

}
