package app.core.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.core.auth.UserCredentials;
import app.core.exceptions.CouponSystemException;
import app.core.services.AdminServiceImpl;
import app.core.services.CompanyServiceImpl;
import app.core.services.CustomerServiceImpl;

@Component
public class LoginManager {

	@Autowired
	private AdminServiceImpl adminServiceImpl;
	@Autowired
	private CompanyServiceImpl companyServiceImpl;
	@Autowired
	private CustomerServiceImpl customerServiceImpl;

	public String login(UserCredentials userCredentials) throws CouponSystemException {
		switch (userCredentials.getClientType().name()) {
		case "ADMIN":
			String tokenAdmin = adminServiceImpl.login(userCredentials);
			return tokenAdmin;

		case "COMPANY":
			String tokenCompany = companyServiceImpl.login(userCredentials);
			return tokenCompany;

		case "CUSTOMER":
			String tokenCustomer = customerServiceImpl.login(userCredentials);
			return tokenCustomer;
		}

		return null;

	}
}
