package app.core.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import app.core.entities.Category;
import app.core.entities.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Integer> {

	boolean existsByTitleAndCompanyId(String title, int companyId);

	List<Coupon> findByCompanyId(int companyId);

	List<Coupon> findByCompanyIdAndCategory(int companyId, Category category);

	List<Coupon> findByCompanyIdAndPriceLessThanEqual(int companyId, double price);

	@Query(value = "SELECT * FROM coupon inner join customers_vs_coupons on coupon_id = id where customer_id = ?", nativeQuery = true)
	List<Coupon> findByCustomerId(int customerId);

	@Query(value = "SELECT * FROM coupon inner join customers_vs_coupons on coupon_id = id where customer_id = ? and category = ?", nativeQuery = true)
	List<Coupon> findByCustomerIdAndCategory(int customerId, String category);

	@Query(value = "SELECT * FROM coupon inner join customers_vs_coupons on coupon_id = id where customer_id = ? and price <= ?", nativeQuery = true)
	List<Coupon> findByCustomerIdAndPrice(int customerId, double price);

	@Query(value = "SELECT EXISTS ( SELECT * FROM `customers_vs_coupons`  WHERE (`customer_id` = ?) and (`coupon_id` = ?)) as res", nativeQuery = true)
	int isCouponPurchasedBefore(int customerId, int couponId);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM `customers_vs_coupons`  WHERE (`coupon_id` = ?)", nativeQuery = true)
	void deletePurchaseCouponsByCouponId(int couponId);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM `customers_vs_coupons`  WHERE (`customer_id` = ?)", nativeQuery = true)
	void deletePurchaseCouponsByCustomerId(int customerId);

	@Transactional
	@Modifying
	@Query(value = "INSERT INTO customers_vs_coupons (customer_id, coupon_id) VALUES (?, ?)", nativeQuery = true)
	Coupon addPurchasedCoupon(int customerId, int couponId);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM customers_vs_coupons WHERE coupon_id IN (SELECT id FROM coupon WHERE end_date < CURDATE())", nativeQuery = true)
	void deleteExpiredPurchasedCoupons();

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM coupon WHERE end_date < CURDATE()", nativeQuery = true)
	void deleteExpiredCoupons();
}
