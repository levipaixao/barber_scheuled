package com.scheuled.barber.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "barber_services",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_barber_service",
                        columnNames = {"barber_id", "service_id"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BarberService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "barber_id", nullable = false)
    private Barber barber;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "service_id", nullable = false)
    private ServiceOffering service;
}