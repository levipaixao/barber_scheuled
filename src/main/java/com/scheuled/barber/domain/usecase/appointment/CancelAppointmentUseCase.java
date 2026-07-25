package com.scheuled.barber.domain.usecase.appointment;

import com.scheuled.barber.domain.exception.ValidationException;
import com.scheuled.barber.infra.persistence.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CancelAppointmentUseCase {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Transactional
    public void execute(Long appointmentId) {
        var appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ValidationException("Agendamento não encontrado."));

        appointment.cancel();
    }
}
