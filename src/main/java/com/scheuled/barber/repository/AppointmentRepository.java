package com.scheuled.barber.repository;

import com.scheuled.barber.domain.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // Verifica se já existe um agendamento do barbeiro exatamente no mesmo horário
    boolean existsByBarberIdAndAppointmentDateTime(Long barberId, LocalDateTime dateTime);

    // Busca agendamentos de um cliente dentro de um intervalo de datas (Útil para a regra do limite semanal / 7 dias)
    @Query("SELECT a FROM Appointment a WHERE a.client.id = :clientId AND a.appointmentDateTime BETWEEN :start AND :end")
    List<Appointment> findAppointmentsByClientInPeriod(
            @Param("clientId") Long clientId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    // Retorna o último agendamento realizado por um determinado cliente
    @Query("SELECT a FROM Appointment a WHERE a.client.id = :clientId ORDER BY a.appointmentDateTime DESC LIMIT 1")
    Appointment findLastAppointmentByClient(@Param("clientId") Long clientId);
}
