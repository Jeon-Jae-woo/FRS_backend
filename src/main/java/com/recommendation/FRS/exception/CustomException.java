package com.recommendation.FRS.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class CustomException {

    // 찾지 못한 경우 예외
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class NotFoundException extends RuntimeException{
        public NotFoundException(String message){
            super(message);
        }
    }
}
