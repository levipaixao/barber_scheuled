package com.scheuled.barber.domain.gateway;

import com.scheuled.barber.domain.entity.Appointment;

public interface CalendarGateway {
    String createEvent(Appointment appointment);
    void deleteEvent(String googleEventId);
}
