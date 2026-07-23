package com.scheuled.barber.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "Barbers")
@Entity(name = "Barber")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Barber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 20)
    private String phone;

    private Boolean active;

    public Barber(String name, String email) {
        this.name = name;
        this.email = email;
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }
}
