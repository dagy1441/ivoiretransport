package com.example.ivoiretransportapi.features.bus;

import com.example.ivoiretransportapi.core.common.base.BaseEntity;
import com.example.ivoiretransportapi.features.trip.Trip;
import jakarta.persistence.OneToMany;

import java.util.Set;

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
public class Bus extends BaseEntity {
    private String licensePlate;
    private int capacity;

    @OneToMany(mappedBy = "bus")
    private Set<Trip> trips;
}
