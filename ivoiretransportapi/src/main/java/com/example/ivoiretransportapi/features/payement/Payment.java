package com.example.ivoiretransportapi.features.payement;

import com.example.ivoiretransportapi.core.common.base.BaseEntity;
import com.example.ivoiretransportapi.features.reservation.Reservation;
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
public class Payment extends BaseEntity {
    private String paymentMethod;
    private double amount;
    private String paymentStatus;

    @OneToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;
}
