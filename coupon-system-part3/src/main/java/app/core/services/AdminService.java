package app.core.services;

import java.util.List;

import org.springframework.stereotype.Service;

import app.core.entities.Company;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;

@Service
public interface AdminService {

	Company addCompany(Company company, int adminId) throws CouponSystemException;

	void updateCompany(Company company, int adminId) throws CouponSystemException;

	void deleteCompany(int companyId, int adminId) throws CouponSystemException;

	List<Company> getAllCompanies(int adminId);

	Company getOneCompany(int companyId, int adminId) throws CouponSystemException;

	Customer addCustomer(Customer customer, int adminId) throws CouponSystemException;

	void updateCustomer(Customer customer, int adminId) throws CouponSystemException;

	void deleteCustomer(int customerId, int adminId) throws CouponSystemException;

	List<Customer> getAllCustomers(int adminId);

	Customer getOneCustomer(int customerId, int adminId) throws CouponSystemException;
}
