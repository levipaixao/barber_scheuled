package com.scheuled.barber;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.converter.json.GsonBuilderUtils;

@SpringBootApplication
public class BarberApplication {

	public static void main(String[] args) {
		SpringApplication.run(BarberApplication.class, args);
	}
}