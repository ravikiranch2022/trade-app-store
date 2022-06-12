package com.tradeapp.service;

import com.tradeapp.bean.Trade;
import com.tradeapp.dto.TradeDTO;

import com.tradeapp.exceptions.TradeException;

import java.util.List;

public interface TradeService {
	
	/**
	 * To process the trade
	 * 
	 * @param tradeDTO
	 * @return
	 * @throws TradeException
	 */
	public void addTrade(TradeDTO tradeDTO) throws TradeException;

	/**
	 * Get all trades
	 * 
	 * @return
	 */
	public List<Trade> getAllTrades();

	/**
	 * Get acive trades
	 * 
	 * @return
	 */
	public List<Trade> getActiveTrades();

	
	public List<Trade> getExpiredTrades();


}
