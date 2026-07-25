package com.scheuled.barber.domain.usecase.appointment;

import com.scheuled.barber.domain.entity.Appointment;
import com.scheuled.barber.domain.entity.Barber;
import com.scheuled.barber.domain.entity.Client;
import com.scheuled.barber.domain.entity.ServiceOffering;
import com.scheuled.barber.domain.exception.ValidationException;
import com.scheuled.barber.domain.usecase.appointment.dto.AppointmentRequestData;
import com.scheuled.barber.domain.usecase.appointment.dto.AppointmentResponseData;
import com.scheuled.barber.domain.usecase.appointment.validation.AppointmentValidator;
import com.scheuled.barber.infra.persistence.repository.AppointmentRepository;
import com.scheuled.barber.infra.persistence.repository.BarberRepository;
import com.scheuled.barber.infra.persistence.repository.ClientRepository;
import com.scheuled.barber.infra.persistence.repository.ServiceOfferingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScheduleAppointmentUseCase {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private BarberRepository barberRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ServiceOfferingRepository serviceOfferingRepository;

    @Autowired
    private List<AppointmentValidator> validators;

    @Transactional
    public AppointmentResponseData execute (AppointmentRequestData data){

        Client client = clientRepository.findById(data.clientId())
                .orElseThrow(() -> new ValidationException("Cliente informado não foi encontrado."));

        Barber barber = barberRepository.findById(data.barberId())
                .orElseThrow(() -> new ValidationException("Barbeiro informado não foi encontrado."));

        if (!barber.getActive()){
            throw new ValidationException("O barbeiro selecionado está inativo.");
        }

        ServiceOffering service = serviceOfferingRepository.findById(data.serviceId())
                .orElseThrow(() -> new ValidationException("Serviço informado não foi encontrado."));

        // Executar todas as regras de negócio / validações injetadas no Spring
        validators.forEach(v -> v.appointmentValidator(data));

        // Calcular horário de término (startAt + duração em minutos do serviço)
        LocalDateTime endAt = data.startAt().plusMinutes(service.getDurationMinutes());

        Appointment appointment = new Appointment(
                client,
                barber,
                service,
                data.startAt(),
                endAt
        );

        return new AppointmentResponseData(appointment);
    }
}
