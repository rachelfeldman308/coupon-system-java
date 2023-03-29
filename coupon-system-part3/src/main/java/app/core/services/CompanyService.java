package app.core.services;

import java.util.List;

import org.springframework.stereotype.Service;

import app.core.entities.Category;
import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.exceptions.CouponSystemException;

@Service
public interface CompanyService {

	Coupon addCoupon(Coupon coupon, int companyId) throws CouponSystemException;

	void updateCoupon(Coupon coupon, int companyId) throws CouponSystemException;

	void deleteCoupon(int couponId, int companyId) throws CouponSystemException;

	List<Coupon> getCompanyCoupons(int companyId);

	List<Coupon> getCompanyCouponsByCategory(Category category, int companyId) throws CouponSystemException;

	List<Coupon> getCompanyCouponsByMaxPrice(double maxPrice, int companyId) throws CouponSystemException;

	Company getCompanyDetails(int companyId) throws CouponSystemException;

}
