package com.scheuled.barber.domain.usecase.appointment.validation;

import com.scheuled.barber.domain.usecase.appointment.dto.AppointmentRequestData;

public interface AppointmentValidator {
    void appointmentValidator(AppointmentRequestData data);
}
