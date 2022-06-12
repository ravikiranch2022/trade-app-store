package com.tradeapp.repository;

import com.tradeapp.bean.Trade;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeRepository extends JpaRepository<Trade, String> {

	@Query(value = "SELECT id, max(version) FROM Trade trade WHERE trade.tradeId = ?1 GROUP BY ID")
	public List<Object[]> getMaxVersionByTradeId(String tradeId);

	@Query(value = "SELECT trade FROM Trade trade WHERE trade.tradeId = ?1 ")
	public List<Trade> getAllTradeByTradeId(String tradeId);

	@Query("select t from Trade t where t.maturityDate < CURRENT_DATE and t.expired = 'N'")
	Collection<Trade> findAllExpiringTrades();

}
