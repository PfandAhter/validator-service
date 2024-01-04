package com.hotelreservation.validatorservice.configuration;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Configuration
@RequiredArgsConstructor
public class BcryptGenerator {

    private final PasswordEncoder passwordEncoder;

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    public String passwordEncdoer(String password){
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }
    public boolean passwordDecoder (String currentPassword,String existingPassword){

        if(passwordEncoder.matches(currentPassword,existingPassword)){
            return true;
        }else{
            return false;
        }
    }
}
