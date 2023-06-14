package app.core.services;

import java.util.List;

import org.springframework.stereotype.Service;

import app.core.entities.Category;
import app.core.entities.Coupon;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;

@Service
public interface CustomerService {

	Coupon PurchaseCoupon(int couponId, int customerId) throws CouponSystemException;

	List<Coupon> getCustomerCoupons(int customerId);

	List<Coupon> getAllCoupons();

	List<Coupon> getCustomerCouponsByCategory(Category category, int customerId) throws CouponSystemException;

	List<Coupon> getCustomerCouponsByMaxPrice(double maxPrice, int customerId) throws CouponSystemException;

	Customer getCustomerDetails(int customerId) throws CouponSystemException;
}
