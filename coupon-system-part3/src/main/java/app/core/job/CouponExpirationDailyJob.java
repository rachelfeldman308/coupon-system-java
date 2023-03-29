package app.core.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import app.core.repositories.CouponRepository;

@Component
public class CouponExpirationDailyJob {

	@Autowired
	private CouponRepository couponRepository;

	private final int timeToSleep = 1000 * 60 * 60 * 24;

	@Scheduled(fixedRate = timeToSleep, initialDelay = 1000 * 60)
	public void deleteExpiredCoupons() {
		couponRepository.deleteExpiredPurchasedCoupons();
		couponRepository.deleteExpiredCoupons();
	}

}
