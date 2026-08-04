package com.scheuled.barber.infra.web.controller;

import com.scheuled.barber.domain.usecase.service.dto.ServiceRequestData;
import com.scheuled.barber.domain.usecase.service.dto.ServiceResponseData;
import com.scheuled.barber.domain.usecase.service.dto.ServiceUseCase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/services")
public class ServiceOfferingController {

    @Autowired
    private ServiceUseCase serviceUseCase;

    @PostMapping
    public ResponseEntity<ServiceResponseData> create(
            @RequestBody @Valid ServiceRequestData data,
            UriComponentsBuilder uriBuilder
    ) {
        var output = serviceUseCase.execute(data);
        var uri = uriBuilder.path("/services/{id}").buildAndExpand(output.id()).toUri();
        return ResponseEntity.created(uri).body(output);
    }
}