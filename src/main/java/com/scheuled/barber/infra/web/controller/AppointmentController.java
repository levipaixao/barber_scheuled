package com.scheuled.barber.infra.web.controller;

import com.scheuled.barber.domain.usecase.appointment.CancelAppointmentUseCase;
import com.scheuled.barber.domain.usecase.appointment.GetAvailableSlotsUseCase;
import com.scheuled.barber.domain.usecase.appointment.ScheduleAppointmentUseCase;
import com.scheuled.barber.domain.usecase.appointment.dto.AppointmentRequestData;
import com.scheuled.barber.domain.usecase.appointment.dto.AppointmentResponseData;
import com.scheuled.barber.domain.usecase.appointment.dto.AvailableSlotData;
import com.scheuled.barber.domain.usecase.appointment.dto.QueryAvailabilityData;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private ScheduleAppointmentUseCase scheduleAppointmentUseCase;

    @Autowired
    private CancelAppointmentUseCase cancelAppointmentUseCase;

    @Autowired
    private GetAvailableSlotsUseCase availableSlotsUseCase;

    @PostMapping
    public ResponseEntity<AppointmentResponseData> schedule(
            @RequestBody @Valid AppointmentRequestData data,
            UriComponentsBuilder uriBuilder
        ){

        var appointmentDetail = scheduleAppointmentUseCase.execute(data);
        var uri = uriBuilder.path("/appointments/{id}").buildAndExpand(appointmentDetail.id()).toUri();
        return ResponseEntity.created(uri).body(appointmentDetail);
    }

    @DeleteMapping("/{id}}")
    public ResponseEntity<Void> cancel(@PathVariable Long id){
        cancelAppointmentUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/availability")
    public ResponseEntity<List<AvailableSlotData>> getAvailable(
            @RequestParam Long barberId,
            @RequestParam Long serviceId,
            @RequestParam @DateTimeFormat (iso = DateTimeFormat.ISO.DATE) LocalDate date
    ){
        var query = new QueryAvailabilityData(barberId, serviceId, date);
        var slots =availableSlotsUseCase.execute(query);
        return ResponseEntity.ok(slots);
    }
}
