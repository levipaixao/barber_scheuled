package com.scheuled.barber.domain.usecase.appointment.dto;

import java.time.LocalDateTime;

public record AvailableSlotData(
        LocalDateTime startAt,
        LocalDateTime endAt
) {
}
