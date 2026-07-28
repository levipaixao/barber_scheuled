package com.scheuled.barber.infra.web.controller;

import com.scheuled.barber.domain.entity.Client;
import com.scheuled.barber.domain.usecase.client.dto.ClientRequestData;
import com.scheuled.barber.domain.usecase.client.dto.ClientResponseData;
import com.scheuled.barber.infra.persistence.repository.ClientRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @PostMapping
    public ResponseEntity<ClientResponseData> register(
            @RequestBody @Valid ClientRequestData data,
            UriComponentsBuilder uriBuilder
    ) {
        var client = new Client(null, data.name(), data.phone());
        clientRepository.save(client);

        var uri = uriBuilder.path("/clients/{id}").buildAndExpand(client.getId()).toUri();
        return ResponseEntity.created(uri).body(new ClientResponseData(client));
    }
}
