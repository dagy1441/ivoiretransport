package com.example.ivoiretransportapi.features.ticket;

import com.example.ivoiretransportapi.core.common.base.BaseEntity;
import com.example.ivoiretransportapi.features.reservation.Reservation;
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
public class Ticket extends BaseEntity {
    private String qrCode;

    @OneToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;
}
