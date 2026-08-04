package com.scheuled.barber.infra.security.dto;

public record TokenJwtResponseData(

        String token,
        String type
) {

    public TokenJwtResponseData(String token) {
        this(token, "Bearer");
    }
}
