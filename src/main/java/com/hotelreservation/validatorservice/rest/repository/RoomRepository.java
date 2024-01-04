package com.hotelreservation.validatorservice.rest.repository;

import com.hotelreservation.validatorservice.model.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Room findRoomById(Long roomid);
}
