package com.scheuled.barber.dto.client;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ClientRequestData(

        @NotBlank(message = "O nome é obrigatório")
        String name,

        @NotBlank(message = "O telefone é obrigatório")
        @Pattern(regexp = "\\d{10,11}", message = "O telefone deve conter apenas dígitos (10 ou 11 números com DDD)")
        String phone
) {
}
