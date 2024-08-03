package com.example.ivoiretransportapi.features.reservation;

import com.example.ivoiretransportapi.core.common.base.BaseEntity;
import com.example.ivoiretransportapi.features.payement.Payment;
import com.example.ivoiretransportapi.features.promotion.Promotion;
import com.example.ivoiretransportapi.features.ticket.Ticket;
import com.example.ivoiretransportapi.features.trip.Trip;
import com.example.ivoiretransportapi.features.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Reservation extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;

    @OneToOne(mappedBy = "reservation")
    private Ticket ticket;

    @OneToOne(mappedBy = "reservation")
    private Payment payment;

    @ManyToOne
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;
}
