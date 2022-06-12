package com.tradeapp.dao;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.tradeapp.bean.Trade;

public interface TradeDao {

	/**
	 * Save Trade
	 * 
	 * @param trade
	 * @return
	 */
	public Trade save(Trade trade);

	/**
	 * Get All Trade
	 * 
	 * @return
	 */
	public List<Trade> getAllTrades();

	/**
	 * find trade by id
	 */
	Optional<Trade> findById(String id);

	/**
	 * findAllExpiringTrades
	 * @return
	 */
	Collection<Trade> findAllExpiringTrades();

	/**
	 * save all trades
	 * @param trades
	 */
	void saveAll(Iterable<Trade> trades);
}
