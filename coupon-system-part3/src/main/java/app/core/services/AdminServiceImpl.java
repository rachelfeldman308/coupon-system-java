package app.core.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.core.auth.JwtUtil;
import app.core.auth.UserCredentials;
import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;

@Service
@Transactional
public class AdminServiceImpl extends ClientService implements AdminService {

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public String login(UserCredentials userCredentials) throws CouponSystemException {
		if (!(userCredentials.getEmail().equals("admin@admin.com") && userCredentials.getPassword().equals("admin"))) {
			throw new CouponSystemException("Email or password is wrong");
		}
		userCredentials.setId(-1);
		return this.jwtUtil.generateToken(userCredentials);
	}

	@Override
	public Company addCompany(Company company, int adminId) throws CouponSystemException {
		if (companyRepository.existsById(company.getId())) {
			throw new CouponSystemException("company already exsits");

		}
		if (companyRepository.existsByName(company.getName())) {
			throw new CouponSystemException("company name already exsits");
		}

		if (companyRepository.existsByEmail(company.getEmail())) {
			throw new CouponSystemException("company email already exsits");
		}
		return companyRepository.save(company);

	}

	@Override
	public void updateCompany(Company company, int adminId) throws CouponSystemException {
		Company companyFromDB = companyRepository.findById(company.getId())
				.orElseThrow(() -> new CouponSystemException("this company is not exists"));

		if (companyRepository.existsByEmailAndIdNot(company.getEmail(), company.getId())) {
			throw new CouponSystemException("company email to update already exsits");
		}
		if (!(company.getName().equals(companyFromDB.getName()))) {
			throw new CouponSystemException("you canot update the name");
		}
		companyFromDB.setEmail(company.getEmail());
		companyFromDB.setPassword(company.getPassword());
		companyRepository.saveAndFlush(companyFromDB);
	}

	@Override
	public void deleteCompany(int companyId, int adminId) throws CouponSystemException {
		if (!companyRepository.existsById(companyId)) {
			throw new CouponSystemException("company whith id " + companyId + " is not exists");
		}
		for (Coupon coupon : couponRepository.findByCompanyId(companyId)) {
			couponRepository.deletePurchaseCouponsByCouponId(coupon.getId());
		}
		companyRepository.deleteById(companyId);
	}

	@Override
	public List<Company> getAllCompanies(int adminId) {
		return this.companyRepository.findAll();
	}

	@Override
	public Company getOneCompany(int companyId, int adminId) throws CouponSystemException {
		return this.companyRepository.findById(companyId)
				.orElseThrow(() -> new CouponSystemException("company whith id " + companyId + " is not exists"));
	}

	@Override
	public Customer addCustomer(Customer customer, int adminId) throws CouponSystemException {
		if (customerRepository.existsById(customer.getId())) {
			throw new CouponSystemException("this customer already exists");
		}
		if (customerRepository.existsByEmail(customer.getEmail())) {
			throw new CouponSystemException("this customer email is exists");
		}
		return customerRepository.save(customer);

	}

	@Override
	public void updateCustomer(Customer customer, int adminId) throws CouponSystemException {
		Customer customerFromDB = customerRepository.findById(customer.getId())
				.orElseThrow(() -> new CouponSystemException("this customer is not exists"));

		if (customerRepository.existsByEmailAndIdNot(customer.getEmail(), customer.getId())) {
			throw new CouponSystemException("customer email to update already exsits");
		}
		customerFromDB.setFirstName(customer.getFirstName());
		customerFromDB.setLastName(customer.getLastName());
		customerFromDB.setEmail(customer.getEmail());
		customerFromDB.setPassword(customer.getPassword());
		customerRepository.saveAndFlush(customer);
	}

	@Override
	public void deleteCustomer(int customerId, int adminId) throws CouponSystemException {
		if (!customerRepository.existsById(customerId)) {
			throw new CouponSystemException("customer with id " + customerId + " is not exists");
		}
		couponRepository.deletePurchaseCouponsByCustomerId(customerId);
		customerRepository.deleteById(customerId);
	}

	@Override
	public List<Customer> getAllCustomers(int adminId) {
		return this.customerRepository.findAll();
	}

	@Override
	public Customer getOneCustomer(int customerId, int adminId) throws CouponSystemException {
		return this.customerRepository.findById(customerId)
				.orElseThrow(() -> new CouponSystemException("customer with id " + customerId + " is not exists"));
	}

}
