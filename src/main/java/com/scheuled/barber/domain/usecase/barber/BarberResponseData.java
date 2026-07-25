package com.scheuled.barber.domain.usecase.barber;

import com.scheuled.barber.domain.entity.Barber;

public record BarberResponseData(
        Long id,
        String name,
        String email,
        String phone,
        Boolean active
) {
    public BarberResponseData(Barber barber) {
        this(barber.getId(), barber.getName(), barber.getEmail(), barber.getPhone(), barber.getActive());
    }
}
