package com.scheuled.barber.infra.persistence.repository;

import com.scheuled.barber.domain.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query(value = """
        SELECT * FROM appointments a 
        WHERE a.client_id = :clientId 
          AND a.status <> 'CANCELED'
        ORDER BY a.start_at DESC
        LIMIT 1
    """, nativeQuery = true)
    Optional<Appointment> findLastAppointmentByClient(@Param("clientId") Long clientId);

    @Query(value = """
        SELECT * FROM appointments a 
        WHERE a.barber_id = :barberId 
          AND a.status <> 'CANCELED'
          AND a.start_at >= :startOfDay 
          AND a.start_at < :endOfDay
        ORDER BY a.start_at ASC
    """, nativeQuery = true)
    List<Appointment> findActiveAppointmentsByBarberAndDate(
            @Param("barberId") Long barberId,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay
    );

    @Query(value = """
        SELECT COUNT(*) > 0 FROM appointments a
        WHERE a.barber_id = :barberId
          AND a.status <> 'CANCELED'
          AND a.start_at < :endAt
          AND a.end_at > :startAt
    """, nativeQuery = true)
    boolean hasScheduleConflict(
            @Param("barberId") Long barberId,
            @Param("startAt") LocalDateTime startAt,
            @Param("endAt") LocalDateTime endAt
    );
}
