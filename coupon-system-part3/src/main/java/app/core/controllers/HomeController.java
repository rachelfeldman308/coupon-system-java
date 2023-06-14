package app.core.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.core.entities.Coupon;
import app.core.services.HomeServiceImpl;

@RestController
@RequestMapping("/all-coupons")
@CrossOrigin
public class HomeController {

	@Autowired
	private HomeServiceImpl homeServiceImpl;

	@GetMapping()
	public List<Coupon> getAllCoupons() {
		return this.homeServiceImpl.getAllCoupons();
	}
}
