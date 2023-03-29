package app.core.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import app.core.auth.UserCredentials.ClientType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;

@Component
public class JwtUtil extends JwtUtilAbstract<UserCredentials, Integer> {

	@Override
	public String generateToken(UserCredentials userCredentials) {
		Map<String, Object> claims = new HashMap<>();
		// claims.put("id", userCredentials.getId());
		claims.put("email", userCredentials.getEmail());
		claims.put("clientType", userCredentials.getClientType());
		String token = this.createToken(claims, userCredentials.getId());
		return token;
	}

	@Override
	public UserCredentials extractUser(String token) throws JwtException {
		Claims claims = this.extractAllClaims(token);
		int id = Integer.parseInt(claims.getSubject());
		String email = claims.get("email", String.class);
		ClientType clientType = ClientType.valueOf(claims.get("clientType", String.class));
		UserCredentials userCredentials = new UserCredentials(id, email, null, clientType);
		return userCredentials;
	}

}
