package com.iua.soa.exeptions;

public class NotFoundException extends Exception {


	private static final long serialVersionUID = 6728782498313267620L;

	public NotFoundException() {
		
	}

	public NotFoundException(String message) {
		super(message);
		
	}

	public NotFoundException(Throwable cause) {
		super(cause);
	}

	public NotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
