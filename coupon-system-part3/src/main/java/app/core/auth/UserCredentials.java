package app.core.auth;

import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCredentials {

	@Id
	private int id;
	private String email;
	private String password;
	private ClientType clientType;

	public enum ClientType {
		ADMIN, COMPANY, CUSTOMER;
	}
}
