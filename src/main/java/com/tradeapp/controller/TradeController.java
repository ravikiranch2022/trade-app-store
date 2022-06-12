package com.tradeapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tradeapp.bean.Trade;
import com.tradeapp.dto.TradeDTO;
import com.tradeapp.service.TradeService;

import lombok.extern.slf4j.Slf4j;

/**
 * This is the controller called Trade App master
 * @author Ravikiran
 *
 */

@Slf4j
@RestController("/trade")
public class TradeController {

	private TradeService tradeService;

	public TradeController(TradeService tradeService) {
		this.tradeService = tradeService;
	}

	/**
	 * This method is used to process the trade
	 * 
	 * @param tradeDTO
	 * @return success message
	 */
	@PostMapping("/sendTrade")
	public ResponseEntity<String> sendTrade(@RequestBody TradeDTO tradeDTO) {
		log.info("sendTrade");
		tradeService.addTrade(tradeDTO);
		return ResponseEntity.ok("Trade processed successfully");
	}
	
	
	/**
	 * This method is used to process all the trades - Need to improve the performance.
	 * 
	 * @param tradeDTO
	 * @return success message
	 */
	@PostMapping("/sendMultipleTrades")
	public ResponseEntity<String> sendMultipleTrades(@RequestBody List<TradeDTO> tradeDTOList) {
		log.info("sendTrade");
		for(TradeDTO tradeDTO: tradeDTOList) {
			tradeService.addTrade(tradeDTO);
		}
		
		return ResponseEntity.ok("Trade processed successfully");
	}

	/**
	 * This method is used to get all active trades
	 * 
	 * @return trade list
	 */
	@GetMapping("/trades/active")
	public ResponseEntity<List<Trade>> getActiveTrades() {
		List<Trade> trades = tradeService.getActiveTrades();
		if (trades == null || trades.isEmpty())
			return ResponseEntity.status(404).body(new ArrayList<>());
		return ResponseEntity.ok(trades);
	}
	
	/**
	 * This method is used to get all trades.
	 * @return
	 */
	@GetMapping("/trades/getAllTrades")
    public ResponseEntity<List<Trade>> getAllTrades() {
        List<Trade> trades = tradeService.getAllTrades();
        if(trades == null || trades.isEmpty())
            return ResponseEntity.status(404).body(new ArrayList<>());
        return ResponseEntity.ok(trades);
    }

	/**
	 * This method is used to get expired trades.
	 * @return
	 */
	@GetMapping("/trades/getExpiredTrades")
    public ResponseEntity<List<Trade>> getExpiredTrades() {
        List<Trade> trades = tradeService.getExpiredTrades();
        if(trades == null || trades.isEmpty())
            return ResponseEntity.status(404).body(new ArrayList<>());
        return ResponseEntity.ok(trades);
    }
}
