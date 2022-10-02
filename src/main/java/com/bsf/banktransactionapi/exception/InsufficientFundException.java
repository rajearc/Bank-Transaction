package com.bsf.banktransactionapi.exception;

public class InsufficientFundException extends BankServiceException {

    public InsufficientFundException(String message, String errorCode) {
        super(message, errorCode);
    }
}