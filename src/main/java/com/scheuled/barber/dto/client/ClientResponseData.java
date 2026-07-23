package com.scheuled.barber.dto.client;

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
