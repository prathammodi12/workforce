package com.workforce.tracker.common.exception;

import com.workforce.tracker.common.response.CommonResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //Validation Errors
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
    public ResponseEntity<CommonResponse<Object>> handle(Exception ex) {

        CommonResponse<Object> response =
                CommonResponse.failure(ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CommonResponse<Object> handleNotFound(ResourceNotFoundException ex){
        return CommonResponse.failure(ex.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public CommonResponse<Object> handleAccessDenied(AccessDeniedException ex) {
        return CommonResponse.failure("Access Denied");
    }
}
