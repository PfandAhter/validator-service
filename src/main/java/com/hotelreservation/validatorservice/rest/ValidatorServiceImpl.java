package com.hotelreservation.validatorservice.rest;

import com.hotelreservation.validatorservice.api.request.*;
import com.hotelreservation.validatorservice.configuration.BcryptGenerator;
import com.hotelreservation.validatorservice.lib.constants.Constants;
import com.hotelreservation.validatorservice.model.Role;
import com.hotelreservation.validatorservice.model.entity.Balance;
import com.hotelreservation.validatorservice.model.entity.Room;
import com.hotelreservation.validatorservice.model.entity.User;
import com.hotelreservation.validatorservice.rest.exception.AuthException;
import com.hotelreservation.validatorservice.rest.interfaces.IValidatorService;
import com.hotelreservation.validatorservice.rest.repository.BalanceRepository;
import com.hotelreservation.validatorservice.rest.repository.RoomRepository;
import com.hotelreservation.validatorservice.rest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
@Slf4j
public class ValidatorServiceImpl implements IValidatorService {

    private final UserRepository userRepository;

    private final BcryptGenerator bcryptGenerator;

    private final BalanceRepository balanceRepository;

    private final RoomRepository roomRepository;

    private final RestTemplate restTemplate;

    @Override
    public Boolean validateUserRegister(UserAddRequest userAddRequest) throws AuthException {
        if (userRepository.findByEmail(userAddRequest.getUsername()) != null) {
            throw new AuthException(Constants.EMAIL_IN_USE);
        } else if (userRepository.findByUsername(userAddRequest.getUsername()) != null) {
            throw new AuthException(Constants.USERNAME_IN_USE);
        } else {
            return true;
        }
    }

    @Override
    public Boolean validateUserAuth(AuthUserRequest request) throws AuthException {

        if (userRepository.findByUsername(request.getUsername()) == null) {
            throw new AuthException(Constants.USERNAME_OR_PASSWORD_NOT_MATCHED);
        } else if (userRepository.findByUsername(request.getUsername()).getUsername().isEmpty() ||
                !(bcryptGenerator.passwordDecoder(request.getPassword(),
                        userRepository.findByUsername(request.getUsername()).getPassword()))) {
            throw new AuthException(Constants.USERNAME_OR_PASSWORD_NOT_MATCHED);
        } else {
            return true;
        }
    }

    /*@Override
    public Boolean validateGetBalance(Long userid) throws AuthException {


        //TODO call another service instead finding itself.
//        String username = restTemplate.postForObject("http://localhost:8082/jwt/extractUsername",request,String.class);

        User testUser = userRepository.findUserById(userid);

        Balance balance = balanceRepository.findByUserId(userid);

        *//*if(!(restTemplate.postForObject("http://localhost:8082/jwt/checkTokenValid",request,Boolean.class))){
            throw new AuthException(Constants.TOKEN_EXPIRED);
        }else if (balance == null) {
            throw new AuthException(Constants.HAVE_NO_BALANCE);
        }else{
            return true;
        }*//*
        if (balance == null) {
            Balance balanceRepo = new Balance();
            balanceRepo.setAmount(0L);
            balanceRepo.setMoneyCode("TL");
            balanceRepo.setUser(testUser);
            balanceRepository.save(balanceRepo);

            throw new AuthException(Constants.BALANCE_NOT_FOUND);
        } else {
            return true;
        }
    }*/
    @Override
    public Boolean validateGetBalance(BaseRequest request) throws AuthException {
        if(restTemplate.postForObject("http://localhost:8082/jwt/checkTokenValid", request, Boolean.class)){
            String tokenUsername = restTemplate.postForObject("http://localhost:8082/jwt/extractUsername",request,String.class);
            User tokenUser = userRepository.findByUsername(tokenUsername);
            Balance balance = balanceRepository.findByUserId(tokenUser.getId());
            if (balance == null) {
                Balance balanceRepo = new Balance();
                balanceRepo.setAmount(0L);
                balanceRepo.setMoneyCode("TL");
                balanceRepo.setUser(tokenUser);
                balanceRepository.save(balanceRepo);
                throw new AuthException(Constants.BALANCE_NOT_FOUND);
            } else {
                return true;
            }
        }else{
            throw new AuthException(Constants.ACCESS_DENIED);
        }
    }

    @Override
    public Boolean validateBuyRoom(BuyRoomRequest request) throws AuthException {
        if (restTemplate.postForObject("http://localhost:8082/jwt/checkTokenValid", request, Boolean.class)) {
            String tokenUsername = restTemplate.postForObject("http://localhost:8082/jwt/extractUsername", request, String.class);
            User user = userRepository.findByUsername(tokenUsername);
            Balance balanceRepo = balanceRepository.findByUserId(user.getId());
            Room roomRepo = roomRepository.findRoomById(request.getRoomnumber());

            if (!(restTemplate.postForObject("http://localhost:8082/jwt/checkTokenValid", request, Boolean.class))) {
                throw new AuthException(Constants.TOKEN_EXPIRED);
            } else if (roomRepo.getIsAvailable().equals("FALSE")) {
                throw new AuthException(Constants.ROOM_IS_NOT_AVAILABLE);
            } else if (balanceRepo.getAmount() < roomRepository.findById(request.getRoomnumber()).get().getPrice()) {
                throw new AuthException(Constants.INSUFFICIENT_FUNDS);
            } else {
                return true;
            }
        } else {
            throw new AuthException(Constants.ACCESS_DENIED);
        }
    }

    @Override
    public Boolean isManager(BaseRequest request) throws AuthException {
        if (restTemplate.postForObject("http://localhost:8082/jwt/checkTokenValid", request, Boolean.class)) {
            String tokenUsername = restTemplate.postForObject("http://localhost:8082/jwt/extractUsername", request, String.class);

            User user = userRepository.findByUsername(tokenUsername);

            if (user.getRole().equals(Role.MANAGER)) {
                return true;
            } else {
                throw new AuthException(Constants.ACCESS_DENIED);
            }
        } else {
            throw new AuthException(Constants.ACCESS_DENIED);
        }
    }
}
