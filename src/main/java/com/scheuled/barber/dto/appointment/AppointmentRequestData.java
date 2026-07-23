package com.scheuled.barber.dto.appointment;

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
        LocalDateTime startAt
) {
}
