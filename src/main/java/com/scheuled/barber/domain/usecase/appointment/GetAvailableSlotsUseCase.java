package com.scheuled.barber.domain.usecase.appointment;

import com.scheuled.barber.domain.entity.Appointment;
import com.scheuled.barber.domain.entity.Barber;
import com.scheuled.barber.domain.entity.ServiceOffering;
import com.scheuled.barber.domain.exception.ValidationException;
import com.scheuled.barber.domain.usecase.appointment.dto.AvailableSlotData;
import com.scheuled.barber.domain.usecase.appointment.dto.QueryAvailabilityData;
import com.scheuled.barber.infra.persistence.repository.AppointmentRepository;
import com.scheuled.barber.infra.persistence.repository.BarberRepository;
import com.scheuled.barber.infra.persistence.repository.ServiceOfferingRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class GetAvailableSlotsUseCase {

    private static final LocalTime OPENING_TIME = LocalTime.of(8, 0);  // 08:00
    private static final LocalTime CLOSING_TIME = LocalTime.of(20, 0); // 20:00
    private static final int SLOT_INTERVAL_MINUTES = 45; // Intervalo de busca (45 min)

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private BarberRepository barberRepository;

    @Autowired
    private ServiceOfferingRepository serviceRepository;

    public List<AvailableSlotData> execute(QueryAvailabilityData query){

        Barber barber = barberRepository.findById(query.barberId())
                .orElseThrow(() -> new ValidationException("Barbeiro não encontrado"));

        if (!barber.getActive()){
            throw new ValidationException("Barbeiro informado está inativo");
        }

        ServiceOffering service = serviceRepository.findById(query.serviceId())
                .orElseThrow(() -> new ValidationException("Serviço não encontrado"));

        // Definir o intervalo do dia
        LocalDate date = query.date();
        LocalDateTime startOfDay = date.atTime(OPENING_TIME);
        LocalDateTime endOfDay = date.atTime(CLOSING_TIME);

        List<Appointment> existingAppointments = appointmentRepository.findActiveAppointmentsByBarberAndDate(
                query.barberId(),
                startOfDay,
                endOfDay
        );

        List<AvailableSlotData> availableSlotData = new ArrayList<>();
        int serviceDuration = service.getDurationMinutes();
        LocalDateTime currentSlotStart = startOfDay;

        while (!currentSlotStart.plusMinutes(serviceDuration).isAfter(endOfDay)){
            LocalDateTime currentSlotEnd = currentSlotStart.plusMinutes(serviceDuration);

            // se for hoje ignora os anteriores
            if (currentSlotStart.isBefore(LocalDateTime.now())){
                currentSlotStart.plusMinutes(SLOT_INTERVAL_MINUTES);
                continue;
            }

            LocalDateTime candidateStart = currentSlotStart;
            boolean hasOverlap = existingAppointments.stream().anyMatch(apt -> candidateStart.isBefore(apt.getEnd_at())
            && currentSlotEnd.isBefore(apt.getStart_at()));

            if (!hasOverlap){
                availableSlotData.add(new AvailableSlotData(currentSlotStart, currentSlotEnd));
            }

            currentSlotStart = currentSlotStart.plusMinutes(SLOT_INTERVAL_MINUTES);
        }
        return availableSlotData;
    }
}
