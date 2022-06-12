package com.tradeapp.exceptions;

/**
 * Used for trade exception
 * @author Ravkiran
 *
 */
public class TradeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TradeException() {
		super();
	}

	public TradeException(String message) {
		super(message);
	}

	public TradeException(String message, Throwable ex) {
		super(message, ex);
	}
}
