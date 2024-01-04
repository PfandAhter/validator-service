package com.hotelreservation.validatorservice.rest.interfaces;

import com.hotelreservation.validatorservice.api.request.AuthUserRequest;
import com.hotelreservation.validatorservice.api.request.BaseRequest;
import com.hotelreservation.validatorservice.api.request.BuyRoomRequest;
import com.hotelreservation.validatorservice.api.request.UserAddRequest;
import com.hotelreservation.validatorservice.api.response.GetBalanceResponse;
import com.hotelreservation.validatorservice.rest.exception.AuthException;

public interface IValidatorService {

    Boolean validateUserRegister(UserAddRequest request) throws AuthException;

    Boolean validateUserAuth(AuthUserRequest request) throws AuthException;

    Boolean validateGetBalance (BaseRequest request) throws AuthException;

    Boolean validateBuyRoom (BuyRoomRequest request) throws AuthException;

    Boolean isManager (BaseRequest request) throws AuthException;
}
