CREATE TABLE barbers (
                         id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(100) NOT NULL,
                         email VARCHAR(100) NOT NULL UNIQUE,
                         phone VARCHAR(20) NOT NULL,
                         active BOOLEAN DEFAULT TRUE
);

CREATE TABLE clients (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(100) NOT NULL,
                         phone VARCHAR(20) NOT NULL,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE appointments (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              client_id BIGINT NOT NULL,
                              barber_id BIGINT NOT NULL,
                              service_id BIGINT NOT NULL,
                              start_at DATETIME NOT NULL,
                              end_at DATETIME NOT NULL,
                              status VARCHAR(20) NOT NULL,
                              google_event_id VARCHAR(255),
                              created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

                              CONSTRAINT fk_appointment_client FOREIGN KEY (client_id) REFERENCES clients(id),
                              CONSTRAINT fk_appointment_barber FOREIGN KEY (barber_id) REFERENCES barbers(id),
                              CONSTRAINT fk_appointment_service FOREIGN KEY (service_id) REFERENCES services(id)
);

CREATE TABLE services (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(100) NOT NULL,
                          price DECIMAL(10, 2) NOT NULL,
                          duration_minutes INT NOT NULL,
                          active BOOLEAN DEFAULT TRUE
);