package com.scheuled.barber.domain.entity;

import com.scheuled.barber.domain.enums.AppointmentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "appointments")
@Entity(name = "Appointment")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "barber_id", nullable = false)
    private Barber barber;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "service_id", nullable = false)
    private ServiceOffering serviceType;

    @Column(name = "start_at", nullable = false)
    private LocalDateTime start_at;

    @Column(name = "end_at", nullable = false)
    private LocalDateTime end_at;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime update_at;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppointmentStatus status;

    private String googleEventId;

    public Appointment(Client client, Barber barber, ServiceOffering serviceType, LocalDateTime start_At, LocalDateTime end_At) {
        this.client = client;
        this.barber = barber;
        this.serviceType = serviceType;
        this.start_at = start_At;
        this.end_at = end_At;
    }

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.update_at = now;
        if (this.status == null) {
            this.status = AppointmentStatus.SCHEDULED;
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.update_at = LocalDateTime.now();
    }

    public void cancel() {
        this.status = AppointmentStatus.CANCELED;
    }
}
