package com.scheuled.barber.domain.usecase.service.dto;

import com.scheuled.barber.domain.entity.ServiceOffering;
import com.scheuled.barber.infra.persistence.repository.ServiceOfferingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServiceUseCase {
    @Autowired
    private ServiceOfferingRepository serviceOfferingRepository;

    @Transactional
    public ServiceResponseData execute(ServiceRequestData input) {
        var serviceOffering = ServiceOffering.builder()
                .name(input.name())
                .durationMinutes(input.durationMinutes())
                .price(input.price())
                .active(true)
                .build();

        serviceOfferingRepository.save(serviceOffering);

        return new ServiceResponseData(serviceOffering);
    }
}
