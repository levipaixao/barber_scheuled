package com.scheuled.barber.infra.persistence.repository;

import com.scheuled.barber.domain.entity.ServiceOffering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceOfferingRepository extends JpaRepository<ServiceOffering, Long> {

    // Retorna apenas os serviços ativos para exibição na escolha do cliente
    List<ServiceOffering> findByActiveTrue();
}
