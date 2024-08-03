package com.example.ivoiretransportapi.features.tracking;

import com.example.ivoiretransportapi.core.common.base.BaseEntity;
import com.example.ivoiretransportapi.features.baggage.Baggage;
import com.example.ivoiretransportapi.features.courier.Courier;
import com.example.ivoiretransportapi.features.trip.Trip;
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
public class Tracking extends BaseEntity {

    private String status;
    private String location;

    @OneToOne
    @JoinColumn(name = "baggage_id")
    private Baggage baggage;

    @OneToOne
    @JoinColumn(name = "courier_id")
    private Courier courier;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;
}
