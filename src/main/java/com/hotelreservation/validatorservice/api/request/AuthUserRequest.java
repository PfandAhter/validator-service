package com.hotelreservation.validatorservice.api.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthUserRequest extends BaseRequest{

    private String username;
    private String password;
}

