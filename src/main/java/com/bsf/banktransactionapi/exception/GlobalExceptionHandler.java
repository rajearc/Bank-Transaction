package com.bsf.banktransactionapi.exception;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.bsf.banktransactionapi.response.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(HttpServletRequest req, Exception e){
        log.error(e.getMessage(), e);

        String errorMsg = (e.getMessage() == null) ? e.getClass().getSimpleName() : e.getMessage();
        Map<String,Object> error = Collections.singletonMap("error", errorMsg);

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(OperationalException.class)
    public ResponseEntity<?> handleSystemException(HttpServletRequest req, Exception e){
        OperationalException sysEx = (OperationalException) e;

        String errorMsg = (e.getMessage() == null) ? e.getClass().getSimpleName() : e.getMessage();
        log.error(sysEx.getErrorCode() + ": " + sysEx.getMessage());

        ErrorResponse response = new ErrorResponse();
        response.setErrorCode(sysEx.getErrorCode());
        response.setErrorMessage(errorMsg);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BankServiceException.class)
    public ResponseEntity<?> handleBusinessException(HttpServletRequest req, Exception e){
        BankServiceException businessEx = (BankServiceException) e;

        String errorMsg = (e.getMessage() == null) ? e.getClass().getSimpleName() : e.getMessage();
        log.error(  businessEx.getErrorCode() + ": " + businessEx.getMessage());

        ErrorResponse response = new ErrorResponse();
        response.setErrorCode(businessEx.getErrorCode());
        response.setErrorMessage(errorMsg);
        response.setTimestamp(new Date());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { BindException.class, MethodArgumentNotValidException.class, MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class })
    @ResponseBody
    public ResponseEntity<?> handleValidationException(HttpServletRequest req, Exception e){
        log.error(e.getMessage());

        String errorMsg = (e.getMessage() == null) ? e.getClass().getSimpleName() : e.getMessage();

        ErrorResponse response = new ErrorResponse();
        response.setErrorCode("VALIDATION_ERROR");
        response.setErrorMessage(errorMsg);
        response.setTimestamp(new Date());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}