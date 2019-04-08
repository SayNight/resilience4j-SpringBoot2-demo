package com.saynight.common.exception;

public class BusinessException extends RuntimeException{

	private static final long serialVersionUID = 7907789959236787926L;
	
    public BusinessException(String message) {
        super(message);
    }
}
