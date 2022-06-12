package com.tradeapp.service;

import com.tradeapp.bean.Trade;
import com.tradeapp.dao.TradeDao;
import com.tradeapp.dto.TradeDTO;

import com.tradeapp.exceptions.TradeException;
import com.tradeapp.mapper.TradeMapper;
import com.tradeapp.util.DateUtil;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@Slf4j
public class TradeServiceImpl implements TradeService {

	private TradeDao tradeDao;

	
	public TradeServiceImpl(TradeDao tradeDao) {
		this.tradeDao = tradeDao;
	}

	/**
	 * This method is used to process the trades.
	 */
	@Override
	public void addTrade(TradeDTO tradeDTO) {
		log.debug("processing trade with tradeId: {}", tradeDTO.getTradeId());
		try {
			// Ignore if maturityDate is older than today's date
			if (DateUtil.isOldMaturityDate(tradeDTO.getMaturityDate())) {
				throw new TradeException("Maturity date should be less than today date. ");
			}
			Optional<Trade> existingTrade = tradeDao.findById(tradeDTO.getTradeId());
			if (existingTrade.isPresent()) { // If there is an existing trade
				// Ignore and throw exception if version is older than existing version
				if (tradeDTO.getVersion() < existingTrade.get().getVersion()) {
					log.info("Ignoring trade since version received is older than existing version. {}", tradeDTO);
					throw new TradeException(
							"Version received is lower than the existing version for tradeId: " + tradeDTO.getTradeId());
				}
				save(tradeDTO);
			} else { // If it is a new trade
				save(tradeDTO);
			}
		}catch(Exception e) {
			throw new TradeException("Invalid trade format exception: "+e.getMessage());
		}
		
	}
	 
	private Trade save(TradeDTO tradeDTO) {
		Trade trade = TradeMapper.mapToTrade(tradeDTO);
		// Set created Date to current date if input created date is null or blank
		if (ObjectUtils.isEmpty(trade.getCreatedDate())) {
			trade.setCreatedDate(LocalDate.now());
		}
		return tradeDao.save(trade);
	}

	
	
	/**
	 * Get All trade
	 */
	@Override
	public List<Trade> getAllTrades() {
		return tradeDao.getAllTrades();
	}

	/**
	 * Get all active trades
	 */
	@Override
	public List<Trade> getActiveTrades() {
		List<Trade> tradeList = getAllTrades();
		return tradeList.stream().filter(trade -> !"Y".equalsIgnoreCase(trade.getExpired()))
				.collect(Collectors.toList());
	}
	
	/**
	 * Get all expired trades
	 */
	@Override
	public List<Trade> getExpiredTrades() {
		List<Trade> tradeList = tradeDao.getAllTrades();
		return tradeList.stream().filter(trade -> !"N".equalsIgnoreCase(trade.getExpired()))
				.collect(Collectors.toList());
	}


}
