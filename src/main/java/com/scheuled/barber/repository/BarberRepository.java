package com.scheuled.barber.repository;

import com.scheuled.barber.domain.entity.Barber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BarberRepository extends JpaRepository<Barber, Long> {

    List<Barber> findByActiveTrue();
}
