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

    @Query("""
        SELECT a FROM Appointment a 
        WHERE a.client.id = :clientId 
          AND a.status <> 'CANCELED'
        ORDER BY a.startAt DESC
        LIMIT 1
    """)
    Optional<Appointment> findLastActiveAppointmentByClient(@Param("clientId") Long clientId);



    // Busca todos os agendamentos ativos de um barbeiro em um determinado dia
    @Query("""
    SELECT a FROM Appointment a 
    WHERE a.barber.id = :barberId 
      AND a.status <> 'CANCELED'
      AND a.startAt >= :startOfDay 
      AND a.startAt < :endOfDay
    ORDER BY a.startAt ASC
""")
    List<Appointment> findActiveAppointmentsByBarberAndDate(
            @Param("barberId") Long barberId,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay
    );

    @Query("""
        SELECT COUNT(a) > 0 FROM Appointment a
        WHERE a.barber.id = :barberId
          AND a.status <> 'CANCELED'
          AND :startAt < a.endAt
          AND :endAt > a.startAt
    """)
    boolean hasScheduleConflict(
            @Param("barberId") Long barberId,
            @Param("startAt") LocalDateTime startAt,
            @Param("endAt") LocalDateTime endAt
    );
}
