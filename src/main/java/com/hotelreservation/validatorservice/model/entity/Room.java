package com.hotelreservation.validatorservice.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "rooms")
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "floor")
    private int floor;
    @Column(name = "roomsize")
    private int roomsize;
    @Column(name = "price")
    private int price;
    @Column(name = "isAvailable")
    private String isAvailable;
    @Column(name = "member_1")
    private String member1;
    @Column(name = "member_2")
    private String member2;
    @Column(name = "member_3")
    private String member3;
    @Column(name = "member_4")
    private String member4;


}
