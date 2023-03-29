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
import org.springframework.web.bind.annotation.RestController;

import app.core.auth.UserCredentials;
import app.core.entities.Company;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;
import app.core.services.AdminServiceImpl;

@RestController
@RequestMapping("/api/admim")
@CrossOrigin
public class AdminController {

	@Autowired
	private AdminServiceImpl adminServiceImpl;

	@PostMapping(path = "/add-company", headers = { HttpHeaders.AUTHORIZATION })
	public Company addCompany(@RequestBody Company company, HttpServletRequest req) throws CouponSystemException {
		UserCredentials userCredentials = (UserCredentials) req.getAttribute("userCredentials");
		return this.adminServiceImpl.addCompany(company, userCredentials.getId());
	}

	@PutMapping(path = "/update-company", headers = { HttpHeaders.AUTHORIZATION })
	public void updateCompany(@RequestBody Company company, HttpServletRequest req) throws CouponSystemException {
		UserCredentials userCredentials = (UserCredentials) req.getAttribute("userCredentials");
		this.adminServiceImpl.updateCompany(company, userCredentials.getId());

	}

	@DeleteMapping(path = "/delete-company/{companyId}", headers = { HttpHeaders.AUTHORIZATION })
	public void deleteCompany(@PathVariable int companyId, HttpServletRequest req) throws CouponSystemException {
		UserCredentials userCredentials = (UserCredentials) req.getAttribute("userCredentials");
		this.adminServiceImpl.deleteCompany(companyId, userCredentials.getId());

	}

	@GetMapping(path = "/companies", headers = { HttpHeaders.AUTHORIZATION })
	public List<Company> getAllCompanies(HttpServletRequest req) {
		UserCredentials userCredentials = (UserCredentials) req.getAttribute("userCredentials");
		return this.adminServiceImpl.getAllCompanies(userCredentials.getId());
	}

	@GetMapping(path = "/company/{companyId}", headers = { HttpHeaders.AUTHORIZATION })
	public Company getOneCompany(@PathVariable int companyId, HttpServletRequest req) throws CouponSystemException {
		UserCredentials userCredentials = (UserCredentials) req.getAttribute("userCredentials");
		return this.adminServiceImpl.getOneCompany(companyId, userCredentials.getId());
	}

	@PostMapping(path = "/add-customer", headers = { HttpHeaders.AUTHORIZATION })
	public Customer addCustomer(@RequestBody Customer customer, HttpServletRequest req) throws CouponSystemException {
		UserCredentials userCredentials = (UserCredentials) req.getAttribute("userCredentials");
		return this.adminServiceImpl.addCustomer(customer, userCredentials.getId());

	}

	@PutMapping(path = "/update-customer", headers = { HttpHeaders.AUTHORIZATION })
	public void updateCustomer(@RequestBody Customer customer, HttpServletRequest req) throws CouponSystemException {
		UserCredentials userCredentials = (UserCredentials) req.getAttribute("userCredentials");
		this.adminServiceImpl.updateCustomer(customer, userCredentials.getId());

	}

	@DeleteMapping(path = "/delete-customer/{customerId}", headers = { HttpHeaders.AUTHORIZATION })
	public void deleteCustomer(@PathVariable int customerId, HttpServletRequest req) throws CouponSystemException {
		UserCredentials userCredentials = (UserCredentials) req.getAttribute("userCredentials");
		this.adminServiceImpl.deleteCustomer(customerId, userCredentials.getId());

	}

	@GetMapping(path = "/customers", headers = { HttpHeaders.AUTHORIZATION })
	public List<Customer> getAllCustomers(HttpServletRequest req) {
		UserCredentials userCredentials = (UserCredentials) req.getAttribute("userCredentials");
		return this.adminServiceImpl.getAllCustomers(userCredentials.getId());
	}

	@GetMapping(path = "/customer/{customerId}", headers = { HttpHeaders.AUTHORIZATION })
	public Customer getOneCustomer(@PathVariable int customerId, HttpServletRequest req) throws CouponSystemException {
		UserCredentials userCredentials = (UserCredentials) req.getAttribute("userCredentials");
		return this.adminServiceImpl.getOneCustomer(customerId, userCredentials.getId());

	}
}
