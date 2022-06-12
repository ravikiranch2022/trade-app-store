package com.tradeapp.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tradeapp.bean.Trade;
import com.tradeapp.dao.TradeDao;
import com.tradeapp.dto.TradeDTO;

@SpringBootTest
public class TradeServiceTest {
	
	@Autowired
	TradeService tradeService;
	
	@Autowired
	private TradeDao tradeDao;
	
	@Test
	public void testAddTrade() {
		TradeDTO tradeDTO = TradeDTO.builder()
                .tradeId("T6")
                .counterPartyId("CP-6")
                .bookId("B6")
                .version(1)
                .expired("N")
                .maturityDate(LocalDate.parse("20/10/2022", DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .build();
	
		tradeService.addTrade(tradeDTO);
		
		Optional<Trade> trade = tradeDao.findById(tradeDTO.getTradeId());
		Assertions.assertTrue(trade.isPresent());
		
	}

}
