package com.scheuled.barber.domain.usecase.appointment.validation;

import com.scheuled.barber.infra.persistence.repository.AppointmentRepository;
import com.scheuled.barber.domain.usecase.appointment.dto.AppointmentRequestData;
import com.scheuled.barber.infra.persistence.repository.ServiceOfferingRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class BarberAvailabilityValidator implements AppointmentValidator{

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private ServiceOfferingRepository serviceRepository;

    @Override
    public void appointmentValidator(AppointmentRequestData data) {

        var service = serviceRepository.findById(data.serviceId())
                .orElseThrow(() -> new ValidationException("Serviço não encontrado"));

        LocalDateTime endAt = data.start_at().plusMinutes(service.getDurationMinutes());

        boolean hasConflict = appointmentRepository.hasScheduleConflict(
                data.barberId(),
                data.start_at(),
                endAt
        );

        if (hasConflict){
            throw new ValidationException(
                    "O barbeiro já possui um agendamento que atinge o horário solicitado. Escolha outro horário.");
        }
    }
}
