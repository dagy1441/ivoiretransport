package com.example.ivoiretransportapi.features.promotion;

import com.example.ivoiretransportapi.core.common.base.BaseEntity;
import com.example.ivoiretransportapi.features.reservation.Reservation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Promotion extends BaseEntity {
    private String code;
    private double discountPercentage;

    @OneToMany(mappedBy = "promotion")
    private Set<Reservation> reservations;
}
