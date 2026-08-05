package com.scheuled.barber.infra.gateway;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.scheuled.barber.domain.entity.Appointment;
import com.scheuled.barber.domain.gateway.CalendarGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;

@Slf4j
@Component
public class GoogleCalendarGateway implements CalendarGateway {

    @Value("${google.calendar.calendar-id}")
    private String calendarId;

    @Value("${google.calendar.credentials-path}")
    private String credentialsPath;

    @Value("${google.calendar.application-name}")
    private String applicationName;

    private final ResourceLoader resourceLoader;

    public GoogleCalendarGateway(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public String createEvent(Appointment appointment) {
        try {
            Calendar service = getCalendarService();

            Event event = new Event()
                    .setSummary(appointment.getServiceType().getName() + " - " + appointment.getClient().getName())
                    .setDescription("Agendamento via Barber API com " + appointment.getBarber().getName());

            ZoneId zoneId = ZoneId.systemDefault();

            Date startDate = Date.from(appointment.getStart_at().atZone(zoneId).toInstant());
            EventDateTime start = new EventDateTime().setDateTime(new com.google.api.client.util.DateTime(startDate));
            event.setStart(start);

            Date endDate = Date.from(appointment.getEnd_at().atZone(zoneId).toInstant());
            EventDateTime end = new EventDateTime().setDateTime(new com.google.api.client.util.DateTime(endDate));
            event.setEnd(end);

            Event createdEvent = service.events().insert(calendarId, event).execute();
            log.info("Evento criado no Google Calendar com sucesso ID: {}", createdEvent.getId());

            return createdEvent.getId();

        } catch (Exception e) {
            log.error("Erro ao sincronizar evento no Google Calendar: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public void deleteEvent(String googleEventId) {
        if (googleEventId == null || googleEventId.isBlank()) return;
        try {
            Calendar service = getCalendarService();
            service.events().delete(calendarId, googleEventId).execute();
            log.info("Evento removido do Google Calendar ID: {}", googleEventId);
        } catch (Exception e) {
            log.error("Erro ao remover evento no Google Calendar: {}", e.getMessage());
        }
    }

    private Calendar getCalendarService() throws Exception {
        try (InputStream in = resourceLoader.getResource(credentialsPath).getInputStream()) {
            GoogleCredential credential = GoogleCredential.fromStream(in)
                    .createScoped(Collections.singleton(CalendarScopes.CALENDAR));

            return new Calendar.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    GsonFactory.getDefaultInstance(),
                    credential
            ).setApplicationName(applicationName).build();
        }
    }
}