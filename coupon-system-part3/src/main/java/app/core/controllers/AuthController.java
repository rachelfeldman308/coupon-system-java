package app.core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.core.auth.UserCredentials;
import app.core.exceptions.CouponSystemException;
import app.core.login.LoginManager;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private LoginManager loginManager;

	@PostMapping("/login")
	public String login(@RequestBody UserCredentials userCredentials) throws CouponSystemException {
		return loginManager.login(userCredentials);
	}

}
