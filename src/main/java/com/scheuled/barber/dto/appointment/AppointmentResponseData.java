package com.scheuled.barber.dto.appointment;

import com.scheuled.barber.domain.entity.Appointment;
import com.scheuled.barber.domain.enums.AppointmentStatus;

import java.time.LocalDateTime;

public record AppointmentResponseData(
        Long id,
        String clientName,
        String barberName,
        String serviceName,
        LocalDateTime startAt,
        LocalDateTime endAt,
        AppointmentStatus status,
        String googleEventId
) {
    public AppointmentResponseData(Appointment appointment){
        this(
                appointment.getId(),
                appointment.getClient().getName(),
                appointment.getBarber().getName(),
                appointment.getServiceType().getName(),
                appointment.getStart_at(),
                appointment.getEnd_at(),
                appointment.getStatus(),
                appointment.getGoogleEventId()
        );
    }
}
