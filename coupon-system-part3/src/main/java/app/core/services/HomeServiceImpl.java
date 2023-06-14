package app.core.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.core.entities.Coupon;
import app.core.repositories.CouponRepository;

@Service
@Transactional
public class HomeServiceImpl implements HomeService {

	@Autowired
	private CouponRepository couponRepository;

	@Override
	public List<Coupon> getAllCoupons() {
		return couponRepository.findAll();

	}

}
