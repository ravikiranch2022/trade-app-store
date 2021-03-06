package com.tradeapp.job;

import java.time.LocalDate;
import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tradeapp.bean.Trade;
import com.tradeapp.dao.TradeDao;


@SpringBootTest
public class ScheduledJobTests {

	@Autowired
	private TradeDao tradeDao;

	@Autowired
	private ScheduledJob scheduledJob;

	@Test
	public void testUpdateExpiredStatus() {
		scheduledJob.updateExpiredStatus();

		Collection<Trade> allExpiringTrades = tradeDao.findAllExpiringTrades();

		LocalDate currentDate = LocalDate.now();
		allExpiringTrades.forEach(trade -> {
			Assertions.assertTrue(trade.getMaturityDate().compareTo(currentDate) < 0);
			Assertions.assertEquals("N", trade.getExpired());
		});

	}

}
