package com.scheuled.barber.infra.web.controller;

import com.scheuled.barber.domain.entity.Barber;
import com.scheuled.barber.domain.usecase.barber.BarberRequestData;
import com.scheuled.barber.domain.usecase.barber.BarberResponseData;
import com.scheuled.barber.infra.persistence.repository.BarberRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/barbers")
public class BarberController {

    @Autowired
    private BarberRepository barberRepository;

    @PostMapping
    public ResponseEntity<BarberResponseData> register(
            @RequestBody @Valid BarberRequestData data,
            UriComponentsBuilder uriBuilder
    ){
        var barber = new Barber(null, data.email(),data.name(), data.phone(), true);
        barberRepository.save(barber);

        var uri = uriBuilder.path("/barbers/{id}").buildAndExpand(barber.getId()).toUri();
        return ResponseEntity.created(uri).body(new BarberResponseData(barber));
    }

    @GetMapping
    public ResponseEntity<List<BarberResponseData>> listAll() {
        var barbers = barberRepository.findAll().stream()
                .map(BarberResponseData::new)
                .toList();
        return ResponseEntity.ok(barbers);
    }
}
