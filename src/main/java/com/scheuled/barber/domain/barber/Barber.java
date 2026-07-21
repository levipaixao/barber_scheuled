package com.scheuled.barber.domain.barber;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "Barbers")
@Entity(name = "Barber")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Barber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String email;

    private String name;

    private String phone;

    private Boolean active;
}
