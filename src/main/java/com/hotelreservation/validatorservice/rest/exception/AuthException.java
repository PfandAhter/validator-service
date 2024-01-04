package com.hotelreservation.validatorservice.rest.exception;

import lombok.Getter;

public class AuthException extends Exception{

    @Getter
    private final String message;

    public AuthException(){
        super();
        this.message = null;
    }

    public AuthException(String message){
        super();
        this.message = message;
    }
}
