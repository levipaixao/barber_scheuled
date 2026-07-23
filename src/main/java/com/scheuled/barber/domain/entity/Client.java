package com.scheuled.barber.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "Clients")
@Entity(name = "Client")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 20)
    private String phone;

    public Client(String name, String phone){
        this.name = name;
        this.phone = phone;
    }
}
