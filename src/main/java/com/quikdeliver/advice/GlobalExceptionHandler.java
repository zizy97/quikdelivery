package com.quikdeliver.advice;

import com.quikdeliver.advice.exception.APIError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.AuthenticationException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public APIError EntityNotFoundException(EntityNotFoundException ex){
        System.out.println(ex.getMessage());
        APIError apiError= new APIError();
        apiError.addCommonError("Entity not found on server");
        return apiError;
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public APIError Exception(Exception ex){
        log.error("exception occur-{}",ex.getMessage());
        APIError apiError= new APIError();
        apiError.addCommonError(ex.getMessage());
        return apiError;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public APIError MethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex){
        System.out.println(ex.getMessage());
        APIError apiError= new APIError();
        apiError.addCommonError("Invalid path param");
        return apiError;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        List<ObjectError> globalErrors= result.getGlobalErrors();

        APIError apiError= new APIError();
        for(FieldError fieldError:fieldErrors){
            apiError.addFieldError(fieldError.getCode(), fieldError.getField(), fieldError.getDefaultMessage(), fieldError.getRejectedValue()!=null?fieldError.getRejectedValue().toString():"");
        }
        for(ObjectError globalError:globalErrors){
//
        }
        return new ResponseEntity(apiError,headers,HttpStatus.BAD_REQUEST);

    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        System.out.println(ex.toString());
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        APIError apiError= new APIError();
        //System.out.println(ex.getMostSpecificCause());
        apiError.addCommonError(ex.getMostSpecificCause().toString());
        return new ResponseEntity(apiError,headers,HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        APIError apiError= new APIError();
        apiError.addCommonError(ex.toString().split(":")[1]);
        return new ResponseEntity(apiError,headers,HttpStatus.BAD_REQUEST);
    }


}
