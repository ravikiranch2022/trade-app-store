package com.tradeapp.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.tradeapp.exceptions.TradeException;

@ControllerAdvice
public class TradeExceptionHandler {

	@ExceptionHandler(value = TradeException.class)
	public ResponseEntity<Object> exception(TradeException ex) {
		return new ResponseEntity<>("Exception occurred." + ex.getMessage(), HttpStatus.CONFLICT);
	}
}
