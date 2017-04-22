package com.checkout;

@SuppressWarnings("serial")
public class NoPriceForProductException extends Exception {
	public NoPriceForProductException() {
	}

	public NoPriceForProductException(String message) {
		super(message);
	}
}
