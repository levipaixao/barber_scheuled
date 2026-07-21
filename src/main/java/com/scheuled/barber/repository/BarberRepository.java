package com.scheuled.barber.repository;

import com.scheuled.barber.domain.barber.Barber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BarberRepository extends JpaRepository<Barber, Long> {
    
}
