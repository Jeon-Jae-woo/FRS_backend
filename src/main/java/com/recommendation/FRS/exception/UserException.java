package com.recommendation.FRS.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class UserException{
    /*
    // 해당 유저를 찾지 못한 경우
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class UserNotFoundException extends RuntimeException{
        public UserNotFoundException(String message){
            super(message);
        }
    }
*/
    // 회원가입시 유저가 이미 존재하는 경우
    @ResponseStatus(HttpStatus.CONFLICT)
    public static class UserAlreadyExistException extends RuntimeException{
        public UserAlreadyExistException(String message){
            super(message);
        }
    }

    //인증 오류
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public static class UserAuthenticationException extends RuntimeException{
        public UserAuthenticationException(String message) { super(message); }
    }
}
