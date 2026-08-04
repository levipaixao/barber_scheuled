package com.scheuled.barber.domain.usecase.appointment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AppointmentRequestData(

        @NotNull(message = "O ID do cliente é obrigatório")
        Long clientId,

        @NotNull(message = "O ID do barbeiro é obrigatório")
        Long barberId,

        @NotNull(message = "O ID do serviço é obrigatório")
        Long serviceId,

        @NotNull(message = "A data e hora de início são obrigatórias")
        @Future(message = "A data do agendamento deve ser no futuro")
        @JsonProperty("start_at")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime start_at
) {
}
