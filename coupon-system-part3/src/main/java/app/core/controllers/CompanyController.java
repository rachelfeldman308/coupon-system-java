package app.core.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.core.auth.UserCredentials;
import app.core.entities.Category;
import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.exceptions.CouponSystemException;
import app.core.services.CompanyServiceImpl;

@RestController
@RequestMapping("api/company")
@CrossOrigin
public class CompanyController {

	@Autowired
	private CompanyServiceImpl companyServiceImpl;

	@PostMapping(path = "/add-coupon", headers = { HttpHeaders.AUTHORIZATION })
	public Coupon addCoupon(@RequestBody Coupon coupon, HttpServletRequest req) throws CouponSystemException {
		UserCredentials userCredentials = (UserCredentials) req.getAttribute("userCredentials");
		return this.companyServiceImpl.addCoupon(coupon, userCredentials.getId());
	}

	@PutMapping(path = "/update-coupon", headers = { HttpHeaders.AUTHORIZATION })
	public void updateCoupon(@RequestBody Coupon coupon, HttpServletRequest req) throws CouponSystemException {
		UserCredentials userCredentials = (UserCredentials) req.getAttribute("userCredentials");
		this.companyServiceImpl.updateCoupon(coupon, userCredentials.getId());

	}

	@DeleteMapping(path = "/delete-coupon/{couponId}", headers = { HttpHeaders.AUTHORIZATION })
	public void deleteCoupon(@PathVariable int couponId, HttpServletRequest req) throws CouponSystemException {
		UserCredentials userCredentials = (UserCredentials) req.getAttribute("userCredentials");
		this.companyServiceImpl.deleteCoupon(couponId, userCredentials.getId());

	}

	@GetMapping(path = "/company-coupons", headers = { HttpHeaders.AUTHORIZATION })
	public List<Coupon> getCompanyCoupons(HttpServletRequest req) {
		UserCredentials userCredentials = (UserCredentials) req.getAttribute("userCredentials");
		return this.companyServiceImpl.getCompanyCoupons(userCredentials.getId());
	}

	@GetMapping(path = "/coupons-by-category", headers = { HttpHeaders.AUTHORIZATION })
	public List<Coupon> getCompanyCouponsByCategory(@RequestParam Category category, HttpServletRequest req)
			throws CouponSystemException {
		UserCredentials userCredentials = (UserCredentials) req.getAttribute("userCredentials");
		return this.companyServiceImpl.getCompanyCouponsByCategory(category, userCredentials.getId());
	}

	@GetMapping(path = "/coupons-by-max-price", headers = { HttpHeaders.AUTHORIZATION })
	public List<Coupon> getCompanyCouponsByMaxPrice(@RequestParam double maxPrice, HttpServletRequest req)
			throws CouponSystemException {
		UserCredentials userCredentials = (UserCredentials) req.getAttribute("userCredentials");
		return this.companyServiceImpl.getCompanyCouponsByMaxPrice(maxPrice, userCredentials.getId());
	}

	@GetMapping(path = "/company-details", headers = { HttpHeaders.AUTHORIZATION })
	public Company getCompanyDetails(HttpServletRequest req) throws CouponSystemException {
		UserCredentials userCredentials = (UserCredentials) req.getAttribute("userCredentials");
		return this.companyServiceImpl.getCompanyDetails(userCredentials.getId());
	}
}
