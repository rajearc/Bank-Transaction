package com.bsf.banktransactionapi.exception;

import org.springframework.http.HttpStatus;

import java.util.Date;

public class BankServiceException extends RuntimeException {
    private final String errorCode;

    private final HttpStatus httpStatus;



    public BankServiceException(String message, String errorCode) {
        super(message);

        this.errorCode = errorCode;
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

    public BankServiceException(String message, String errorCode, HttpStatus httpStatus) {
        super(message);

        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }

    public BankServiceException(String message, String errorCode, HttpStatus httpStatus, Date timestamp) {
        this(message,errorCode,httpStatus);

    }

    public String getErrorCode() {
        return errorCode;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}