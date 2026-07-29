package com.scheuled.barber.domain.usecase.appointment.validation;

import com.scheuled.barber.domain.entity.Appointment;
import com.scheuled.barber.infra.persistence.repository.AppointmentRepository;
import com.scheuled.barber.domain.usecase.appointment.dto.AppointmentRequestData;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class ClientWeeklyLimitValidator implements AppointmentValidator{

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public void appointmentValidator(AppointmentRequestData data) {

        Optional<Appointment> lastAppointment = appointmentRepository.findLastAppointmentByClient(data.clientId());

        if (lastAppointment.isPresent()){
            LocalDateTime nextAllowedDate = lastAppointment.get().getStart_at().plusDays(7);

            if (data.start_at().isBefore(nextAllowedDate)){
                throw new ValidationException(
                        "O cliente só pode agendar um novo serviço após 7 dias do último agendamento. " +
                        "Próxima data disponível a partir de: " +  nextAllowedDate.toLocalDate());
            }
        }
    }
}
