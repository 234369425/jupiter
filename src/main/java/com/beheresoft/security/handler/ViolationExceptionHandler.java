package com.beheresoft.security.handler;

import com.beheresoft.security.result.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

/**
 * @author Aladi
 */
@RestControllerAdvice
public class ViolationExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public Result onException(ConstraintViolationException e) {

        return Result.err(e.getLocalizedMessage());

    }

}
