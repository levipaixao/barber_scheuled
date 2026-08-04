package com.scheuled.barber.domain.usecase.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ServiceRequestData(
        @NotBlank(message = "O nome do serviço é obrigatório")
        String name,

        @NotNull(message = "A duração em minutos é obrigatória")
        @Positive(message = "A duração deve ser maior que zero")
        Integer durationMinutes,

        @NotNull(message = "O preço é obrigatório")
        @Positive(message = "O preço deve ser maior que zero")
        BigDecimal price
) {
}
