package com.bsf.banktransactionapi.exception;

public class OperationalException extends Exception {
    private final String errorCode;

    public String getErrorCode() {
        return errorCode;
    }

    public OperationalException(String message, String errorCode) {
        super(message);

        this.errorCode = errorCode;
    }
}