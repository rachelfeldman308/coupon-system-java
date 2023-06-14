package app.core.services;

import java.util.List;

import org.springframework.stereotype.Service;

import app.core.entities.Coupon;

@Service
public interface HomeService {

	List<Coupon> getAllCoupons();

}
