package app.core.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.core.auth.UserCredentials;
import app.core.entities.Category;
import app.core.entities.Coupon;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;
import app.core.services.CustomerServiceImpl;

@RestController
@RequestMapping("api/customer")
@CrossOrigin
public class CustomerController {

	@Autowired
	private CustomerServiceImpl customerServiceImpl;

	@PostMapping(headers = { HttpHeaders.AUTHORIZATION })
	public Coupon PurchaseCoupon(@RequestBody Coupon coupon, HttpServletRequest req) throws CouponSystemException {
		UserCredentials userCredentials = (UserCredentials) req.getAttribute("userCredentials");
		return this.customerServiceImpl.PurchaseCoupon(coupon, userCredentials.getId());
	}

	@GetMapping(path = "/customer-coupons", headers = { HttpHeaders.AUTHORIZATION })
	public List<Coupon> getCustomerCoupons(HttpServletRequest req) {
		UserCredentials userCredentials = (UserCredentials) req.getAttribute("userCredentials");
		return this.customerServiceImpl.getCustomerCoupons(userCredentials.getId());
	}

	@GetMapping(path = "/customer-coupons-by-category", headers = { HttpHeaders.AUTHORIZATION })
	public List<Coupon> getCustomerCouponsByCategory(@RequestParam Category category, HttpServletRequest req)
			throws CouponSystemException {
		UserCredentials userCredentials = (UserCredentials) req.getAttribute("userCredentials");
		return this.customerServiceImpl.getCustomerCouponsByCategory(category, userCredentials.getId());
	}

	@GetMapping(path = "/customer-coupons-by-max-price", headers = { HttpHeaders.AUTHORIZATION })
	public List<Coupon> getCustomerCouponsByMaxPrice(@RequestParam double maxPrice, HttpServletRequest req)
			throws CouponSystemException {
		UserCredentials userCredentials = (UserCredentials) req.getAttribute("userCredentials");
		return this.customerServiceImpl.getCustomerCouponsByMaxPrice(maxPrice, userCredentials.getId());
	}

	@GetMapping(path = "/customer-details", headers = { HttpHeaders.AUTHORIZATION })
	public Customer getCustomerDetails(HttpServletRequest req) throws CouponSystemException {
		UserCredentials userCredentials = (UserCredentials) req.getAttribute("userCredentials");
		return this.customerServiceImpl.getCustomerDetails(userCredentials.getId());
	}
}
