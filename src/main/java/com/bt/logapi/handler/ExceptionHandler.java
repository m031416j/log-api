package com.bt.logapi.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler({Exception.class})
    public static ResponseEntity<Object> handleException(Exception ex) {
        String msg = String.format("Error '%s'", ex.getMessage());
        log.error("Error '{}'", msg);
        return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({IllegalArgumentException.class})
    public static ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        String msg = String.format("Validation error '%s'", ex.getMessage());
        log.error("Validation error '{}'", msg);
        return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
    }

}
