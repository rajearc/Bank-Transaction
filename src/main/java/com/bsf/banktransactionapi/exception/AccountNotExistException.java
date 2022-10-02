package com.bsf.banktransactionapi.exception;

public class AccountNotExistException extends BankServiceException {

    public AccountNotExistException(String message, String errorCode) {
        super(message, errorCode);
    }

}