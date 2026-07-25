package com.scheuled.barber.domain.usecase.appointment.validation;

import com.scheuled.barber.infra.persistence.repository.AppointmentRepository;
import com.scheuled.barber.domain.usecase.appointment.dto.AppointmentRequestData;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BarberAvailabilityValidator implements AppointmentValidator{

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public void appointmentValidator(AppointmentRequestData data) {
        boolean isBarberBusy = appointmentRepository.existsByBarberIdAndAppointmentDateTime(
                data.barberId(),
                data.startAt());

        if (isBarberBusy){
            throw new ValidationException("O barbeiro escolhido já possui um agendamento neste horário!");
        }
    }
}
