package com.example.ivoiretransportapi.features.baggage;

import com.example.ivoiretransportapi.core.common.base.BaseEntity;
import com.example.ivoiretransportapi.features.tracking.Tracking;
import com.example.ivoiretransportapi.features.user.User;
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
public class Baggage extends BaseEntity {
    private String description;
    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(mappedBy = "baggage")
    private Tracking tracking;
}
