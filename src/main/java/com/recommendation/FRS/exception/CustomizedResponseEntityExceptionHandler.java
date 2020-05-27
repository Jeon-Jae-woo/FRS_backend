package com.recommendation.FRS.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestController
@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> AllException(Exception e, WebRequest request){
        ExceptionMessage exceptionMessage =
                new ExceptionMessage(LocalDateTime.now(), e.getMessage(), request.getDescription(false));
        return new ResponseEntity(exceptionMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NullPointerException.class)
    public final ResponseEntity<Object> NullPointException(Exception e, WebRequest request){
        ExceptionMessage exceptionMessage =
                new ExceptionMessage(LocalDateTime.now(), "nullPointException", request.getDescription(false));
        return new ResponseEntity(exceptionMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<Object> IllegalArgumentException(Exception e, WebRequest webRequest){
        ExceptionMessage exceptionMessage =
                new ExceptionMessage(LocalDateTime.now(), e.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity(exceptionMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserException.UserNotFoundException.class)
    public final ResponseEntity<Object> UserNotFoundException(Exception e, WebRequest request){
        ExceptionMessage exceptionMessage =
                new ExceptionMessage(LocalDateTime.now(), e.getMessage(), request.getDescription(false));
        return new ResponseEntity(exceptionMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserException.UserAlreadyExistException.class)
    public final ResponseEntity<Object> UserAlreadyExistException(Exception e, WebRequest request){
        ExceptionMessage exceptionMessage =
                new ExceptionMessage(LocalDateTime.now(), e.getMessage(), request.getDescription(false));
        return new ResponseEntity(exceptionMessage, HttpStatus.CONFLICT);
    }

    // 인증관련 에러는 Security에서 알아서 처리해 주는듯 ?
    @ExceptionHandler(UserException.UserAuthenticationException.class)
    public final ResponseEntity<Object> UserAuthenticationException(Exception e, WebRequest request){
        ExceptionMessage exceptionMessage =
                new ExceptionMessage(LocalDateTime.now(), e.getMessage(), request.getDescription(false));
        return new ResponseEntity(exceptionMessage, HttpStatus.UNAUTHORIZED);
    }


}
