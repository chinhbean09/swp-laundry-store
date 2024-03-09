package com.project.SWP391.error;

import com.project.SWP391.responses.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.DuplicateFormatFlagsException;
import java.util.NoSuchElementException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice()
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Object> handleSqlIntegrityException(HttpServletRequest req,SQLIntegrityConstraintViolationException ex){

        String error = "Unable to submit post: " + ex.getMessage();
        return buildResponseEntity(new ErrorResponse(HttpStatus.BAD_REQUEST, error));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> handleNoSuchElementException(HttpServletRequest req,NoSuchElementException ex){
        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND);
        response.setMessage("data not existent: " + req.getRequestURI());
        return buildResponseEntity(response);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleNoSuchElementException(HttpServletRequest req,RuntimeException ex){
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST);
        response.setMessage("Invalid processing");
        return buildResponseEntity(response);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> handleNullPointerException(HttpServletRequest req,NullPointerException ex){
        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND);
        response.setMessage("data not existent: " + req.getRequestURI());
        return buildResponseEntity(response);
    }

//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<Object> handleIllegalArgumentException(HttpServletRequest req,IllegalArgumentException ex){
//        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND);
//        response.setMessage("data not existent: " + req.getRequestURI());
//        return buildResponseEntity(response);
//    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Object> handleExpiredJwtException(HttpServletRequest req,ExpiredJwtException ex){
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST);
        response.setMessage("data not existent: " + req.getRequestURI());
        return buildResponseEntity(response);
    }
    @ExceptionHandler(DuplicateFormatFlagsException.class)
    public ResponseEntity<Object> handleDuplicatedException(HttpServletRequest req,DuplicateFormatFlagsException ex){
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST);
        response.setMessage("Data is duplicated " + req.getRequestURI());
        return buildResponseEntity(response);
    }
    private ResponseEntity<Object> buildResponseEntity(ErrorResponse errorResponse){
        return new ResponseEntity<Object>(errorResponse, errorResponse.getStatus());
    }

}