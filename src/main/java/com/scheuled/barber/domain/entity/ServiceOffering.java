package com.scheuled.barber.domain.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity(name = "ServiceOffering")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ServiceOffering {

    private Long id;

    private String name;

    private Integer durationMinutes;

    private BigDecimal price;

    private Boolean active;
}
