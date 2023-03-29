package app.core.services;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.core.auth.JwtUtil;
import app.core.auth.UserCredentials;
import app.core.entities.Category;
import app.core.entities.Coupon;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;

@Service
@Transactional
public class CustomerServiceImpl extends ClientService implements CustomerService {

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public String login(UserCredentials userCredentials) throws CouponSystemException {
		if (customerRepository.existsByEmailAndPassword(userCredentials.getEmail(), userCredentials.getPassword())) {
			Customer customer = customerRepository.findByEmailAndPassword(userCredentials.getEmail(),
					userCredentials.getPassword());
			userCredentials.setId(customer.getId());
			return this.jwtUtil.generateToken(userCredentials);
		}
		throw new CouponSystemException("Incorrect email or password ");
	}

	@Override
	public Coupon PurchaseCoupon(Coupon coupon, int customerId) throws CouponSystemException {
		Coupon couponFromDb = couponRepository.findById(coupon.getId())
				.orElseThrow(() -> new CouponSystemException("coupon not found"));
		if (couponRepository.isCouponPurchasedBefore(customerId, coupon.getId()) > 0) {
			throw new CouponSystemException("coupon is already purchased before");
		}
		if (couponFromDb.getAmount() <= 0) {
			throw new CouponSystemException("coupon amount is 0");
		}

		if (couponFromDb.getEndDate().before(Date.valueOf(LocalDate.now()))) {
			throw new CouponSystemException("coupon is expired");
		}
		coupon.setAmount(coupon.getAmount() - 1);
		couponRepository.saveAndFlush(coupon);
		return couponRepository.addPurchasedCoupon(customerId, coupon.getId());
	}

	@Override
	public List<Coupon> getCustomerCoupons(int customerId) {
		List<Coupon> coupons = couponRepository.findByCustomerId(customerId);
		return coupons;
	}

	@Override
	public List<Coupon> getCustomerCouponsByCategory(Category category, int customerId) throws CouponSystemException {
		return couponRepository.findByCustomerIdAndCategory(customerId, category.name());
	}

	@Override
	public List<Coupon> getCustomerCouponsByMaxPrice(double maxPrice, int customerId) throws CouponSystemException {
		return couponRepository.findByCustomerIdAndPrice(customerId, maxPrice);
	}

	@Override
	public Customer getCustomerDetails(int customerId) throws CouponSystemException {
		return customerRepository.findById(customerId)
				.orElseThrow(() -> new CouponSystemException("customer  " + customerId + " is not found"));
	}

}
