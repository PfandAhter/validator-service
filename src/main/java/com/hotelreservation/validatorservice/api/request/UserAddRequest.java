package com.hotelreservation.validatorservice.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UserAddRequest {
    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private String password;
}
