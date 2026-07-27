package com.scheuled.barber.domain.usecase.appointment.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record QueryAvailabilityData(
        @NotNull(message = "O ID do barbeiro é obrigatório")
        Long barberId,

        @NotNull(message = "O ID do serviço é obrigatório")
        Long serviceId,

        @NotNull(message = "A data da consulta é obrigatória")
        @FutureOrPresent(message = "A data não pode ser no passado")
        LocalDate date
) {
}
