package com.example.ivoiretransportapi.features.trip;

import com.example.ivoiretransportapi.core.common.base.BaseEntity;
import com.example.ivoiretransportapi.features.bus.Bus;
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
public class Trip extends BaseEntity {

    private String departureLocation;
    private String arrivalLocation;
    private String departureTime;
    private String arrivalTime;
    private double price;

    @OneToMany(mappedBy = "trip")
    private Set<Reservation> reservations;

    @ManyToOne
    @JoinColumn(name = "bus_id")
    private Bus bus;
}
