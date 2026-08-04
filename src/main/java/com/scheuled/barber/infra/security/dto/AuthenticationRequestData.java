package com.scheuled.barber.infra.security.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequestData(

        @NotBlank(message = "O login é obrigatório")
        String login,

        @NotBlank(message = "A senha é obrigatória")
        String password
) {}
