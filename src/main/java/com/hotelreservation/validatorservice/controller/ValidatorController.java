package com.hotelreservation.validatorservice.controller;


import com.hotelreservation.validatorservice.api.request.*;
import com.hotelreservation.validatorservice.lib.constants.Constants;
import com.hotelreservation.validatorservice.rest.ValidatorServiceImpl;
import com.hotelreservation.validatorservice.rest.exception.AuthException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/validate")
@RequiredArgsConstructor
@Slf4j

public class ValidatorController {

    private final ValidatorServiceImpl validatorService;

    @PostMapping(path = "/register")
    public ResponseEntity<Boolean> checkUserCanRegister(@RequestBody UserAddRequest request)throws AuthException {
        return ResponseEntity.ok(validatorService.validateUserRegister(request));
    }

    @PostMapping(path = "/login")
    public ResponseEntity<Boolean> checkUserCredentials(@RequestBody AuthUserRequest request) throws AuthException{
        return ResponseEntity.ok(validatorService.validateUserAuth(request));
    }

    /*@GetMapping(path = "/getbalance/{userid}")
    public ResponseEntity<Boolean> checkGetBalance (@PathVariable("userid") Long userid) throws AuthException{
        return ResponseEntity.ok(validatorService.validateGetBalance(userid));
    }*/

    @PostMapping(path = "/getbalance")
    public ResponseEntity<Boolean> underTestCheckGetBalance (@RequestBody BaseRequest request) throws AuthException{
        return ResponseEntity.ok(validatorService.validateGetBalance(request));
    }



    @PostMapping(path = "/buyroom")
    public ResponseEntity<Boolean> checkBuyRoom (@RequestBody BuyRoomRequest request) throws AuthException{
        return ResponseEntity.ok(validatorService.validateBuyRoom(request));
    }

    @PostMapping(path = "/ismanager")
    public ResponseEntity<Boolean> checkIsManager ( @RequestBody UserListInRoomsRequest request) throws AuthException{
        return ResponseEntity.ok(validatorService.isManager(request));
    }
}
