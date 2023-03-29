package app.core.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.core.auth.JwtUtil;
import app.core.auth.UserCredentials;
import app.core.entities.Category;
import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.exceptions.CouponSystemException;

@Service
@Transactional
public class CompanyServiceImpl extends ClientService implements CompanyService {

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public String login(UserCredentials userCredentials) throws CouponSystemException {
		if (companyRepository.existsByEmailAndPassword(userCredentials.getEmail(), userCredentials.getPassword())) {
			Company company = companyRepository.findByEmailAndPassword(userCredentials.getEmail(),
					userCredentials.getPassword());
			if (company != null) {
				userCredentials.setId(company.getId());
				return this.jwtUtil.generateToken(userCredentials);

			}
		}
		throw new CouponSystemException("Incorrect email or password ");
	}

	@Override
	public Coupon addCoupon(Coupon coupon, int companyId) throws CouponSystemException {
		if (couponRepository.existsByTitleAndCompanyId(coupon.getTitle(), companyId)) {
			throw new CouponSystemException("coupon with this title is already exists");
		}
		coupon.setCompany(getCompanyDetails(companyId));
		return couponRepository.save(coupon);

	}

	@Override
	public void updateCoupon(Coupon coupon, int companyId) throws CouponSystemException {
		Coupon couponFromDB = couponRepository.findById(coupon.getId())
				.orElseThrow(() -> new CouponSystemException("this coupon is not exists"));
		couponFromDB.setCategory(coupon.getCategory());
		couponFromDB.setTitle(coupon.getTitle());
		couponFromDB.setDescription(coupon.getDescription());
		couponFromDB.setStartDate(coupon.getStartDate());
		couponFromDB.setEndDate(coupon.getEndDate());
		couponFromDB.setAmount(coupon.getAmount());
		couponFromDB.setPrice(coupon.getPrice());
		couponFromDB.setImage(coupon.getImage());
		couponRepository.saveAndFlush(couponFromDB);
	}

	@Override
	public void deleteCoupon(int couponId, int companyId) throws CouponSystemException {
		if (!couponRepository.existsById(couponId)) {
			throw new CouponSystemException("coupon whith id " + couponId + " is not exists");
		}
		couponRepository.deletePurchaseCouponsByCouponId(couponId);
		couponRepository.deleteById(couponId);
	}

	@Override
	public List<Coupon> getCompanyCoupons(int companyId) {
		List<Coupon> coupons = couponRepository.findByCompanyId(companyId);
		return coupons;
	}

	@Override
	public List<Coupon> getCompanyCouponsByCategory(Category category, int companyId) throws CouponSystemException {
		return couponRepository.findByCompanyIdAndCategory(companyId, category);
	}

	@Override
	public List<Coupon> getCompanyCouponsByMaxPrice(double maxPrice, int companyId) throws CouponSystemException {
		return couponRepository.findByCompanyIdAndPriceLessThanEqual(companyId, maxPrice);
	}

	@Override
	public Company getCompanyDetails(int companyId) throws CouponSystemException {
		return companyRepository.findById(companyId)
				.orElseThrow(() -> new CouponSystemException("company  " + companyId + " is not found"));
	}
}
