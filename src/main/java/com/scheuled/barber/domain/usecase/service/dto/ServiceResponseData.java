package com.scheuled.barber.domain.usecase.service.dto;

import com.scheuled.barber.domain.entity.ServiceOffering;

import java.math.BigDecimal;

public record ServiceResponseData(
        Long id,
        String name,
        Integer durationMinutes,
        BigDecimal price,
        Boolean active
) {
    public ServiceResponseData(ServiceOffering service) {
        this(
                service.getId(),
                service.getName(),
                service.getDurationMinutes(),
                service.getPrice(),
                service.getActive()
        );
    }
}
