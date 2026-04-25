package com.workforce.tracker.common.exception;

import com.workforce.tracker.common.response.CommonResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //Vaidation Errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponse<Object> handleValidation(MethodArgumentNotValidException ex){
        String message=ex.getBindingResult()
                .getFieldError()
                .getDefaultMessage();

        return CommonResponse.failure(message);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponse<Object> handleDuplicate(DataIntegrityViolationException ex){
        return CommonResponse.failure("Employee code already exists");
    }

    // Generic Errors
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResponse<Object> handle(Exception e){
        return CommonResponse.failure(e.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CommonResponse<Object> handleNotFound(ResourceNotFoundException ex){
        return CommonResponse.failure(ex.getMessage());
    }
}
