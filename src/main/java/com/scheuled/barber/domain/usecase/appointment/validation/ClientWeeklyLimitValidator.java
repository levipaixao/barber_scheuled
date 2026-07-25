package com.scheuled.barber.domain.usecase.appointment.validation;

import com.scheuled.barber.domain.entity.Appointment;
import com.scheuled.barber.infra.persistence.repository.AppointmentRepository;
import com.scheuled.barber.domain.usecase.appointment.dto.AppointmentRequestData;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ClientWeeklyLimitValidator implements AppointmentValidator{

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public void appointmentValidator(AppointmentRequestData data) {

        Appointment lastAppointment = appointmentRepository.findLastAppointmentByClient(data.clientId());

        if (lastAppointment != null){
            LocalDateTime nextAllowedDate = lastAppointment.getStart_at().plusDays(7);

            if (data.startAt().isBefore(nextAllowedDate)){
                throw new ValidationException(
                        "O cliente só pode agendar um novo serviço após 7 dias do último agendamento. " +
                        "Próxima data disponível a partir de: " +  nextAllowedDate.toLocalDate());
            }
        }
    }
}
