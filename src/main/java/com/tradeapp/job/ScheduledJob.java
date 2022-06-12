package com.tradeapp.job;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tradeapp.bean.Trade;
import com.tradeapp.dao.TradeDao;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ScheduledJob {

	@Autowired
	private TradeDao tradeDao;

	/**
	 * Job to update expired flag to N if the maturity date is crossed. Running this
	 * job Daily once would be enough
	 */
	@Scheduled(cron = "${cron.expression}")
	public void updateExpiredStatus() {
		log.info("Executing schedule for updateExpiredStatus.. ");
		Collection<Trade> allExpiredTrades = tradeDao.findAllExpiringTrades();

		allExpiredTrades.stream().forEach(trade -> {
			trade.setExpired("Y");
		});

		tradeDao.saveAll(allExpiredTrades);
		
		log.info("Updated {} no of records as expired.", allExpiredTrades.size());
	}
}
