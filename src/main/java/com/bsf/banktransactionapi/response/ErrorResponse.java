package com.bsf.banktransactionapi.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
public class ErrorResponse {

    private String errorCode;

    private String errorMessage;

    private Date timestamp;


}
