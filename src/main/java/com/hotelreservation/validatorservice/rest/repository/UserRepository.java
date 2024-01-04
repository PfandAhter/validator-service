package com.hotelreservation.validatorservice.rest.repository;

import com.hotelreservation.validatorservice.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findUserById(Long userid);

    User findByUsername(String username);
}
