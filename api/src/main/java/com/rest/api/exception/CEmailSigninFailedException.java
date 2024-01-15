package com.rest.api.exception;

public class CEmailSigninFailedException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public CEmailSigninFailedException(String msg, Throwable t) {
		super(msg, t);
	}
	
	public CEmailSigninFailedException(String msg) {
		super(msg);
	}
	
	public CEmailSigninFailedException() {
		super();
	}

}
