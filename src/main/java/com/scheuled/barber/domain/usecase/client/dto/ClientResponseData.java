package com.scheuled.barber.domain.usecase.client.dto;

import com.scheuled.barber.domain.entity.Client;

public record ClientResponseData(
        Long id,
        String name,
        String phone
) {
    public ClientResponseData(Client client) {
        this(client.getId(), client.getName(), client.getPhone());
    }
}
