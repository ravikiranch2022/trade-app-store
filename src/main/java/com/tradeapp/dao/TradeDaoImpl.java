package com.tradeapp.dao;

import com.tradeapp.bean.Trade;
import com.tradeapp.repository.TradeRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public class TradeDaoImpl implements TradeDao {

	private TradeRepository tradeRepository;

	public TradeDaoImpl(TradeRepository tradeRepository) {
		this.tradeRepository = tradeRepository;
	}

	/**
	 * Save the trade
	 */
	@Override
	public Trade save(Trade trade) {
		return tradeRepository.save(trade);
	}

	

	/**
	 * Get all trades
	 */
	@Override
	public List<Trade> getAllTrades() {
		return tradeRepository.findAll();
	}

	/**
	 

	/**
	 * find trade by id
	 */
	@Override
	public Optional<Trade> findById(String id) {
		return tradeRepository.findById(id);
	}

	/**
	 * find all expiring trades
	 */
	@Override
    public Collection<Trade> findAllExpiringTrades() {
        return tradeRepository.findAllExpiringTrades();
    }
	
	/**
	 * save all trades
	 */
	@Override
    public void saveAll(Iterable<Trade> trades) {
        tradeRepository.saveAll(trades);
    }

}
