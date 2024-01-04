package com.hotelreservation.validatorservice.rest.repository;

import com.hotelreservation.validatorservice.model.entity.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BalanceRepository extends JpaRepository<Balance,Long> {

    Balance findByUserId(Long userId);

}
