package com.tradeapp.mapper;

import java.time.LocalDate;

import com.tradeapp.bean.Trade;
import com.tradeapp.dto.TradeDTO;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TradeMapper {
	public static Trade mapToTrade(TradeDTO tradeDTO) {
		return Trade.builder().tradeId(tradeDTO.getTradeId()).version(tradeDTO.getVersion())
				.counterPartyId(tradeDTO.getCounterPartyId()).bookId(tradeDTO.getBookId()).expired(tradeDTO.getExpired())
				.maturityDate(tradeDTO.getMaturityDate()).createdDate(LocalDate.now()).expired(tradeDTO.getExpired())
				.build();
	}
}
