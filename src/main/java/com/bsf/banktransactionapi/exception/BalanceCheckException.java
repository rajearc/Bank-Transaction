package com.bsf.banktransactionapi.exception;
public class BalanceCheckException extends OperationalException {

    public BalanceCheckException(String message, String errorCode) {
        super(message, errorCode);
    }

}