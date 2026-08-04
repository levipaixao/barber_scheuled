package com.scheuled.barber.infra.security;

import com.scheuled.barber.domain.entity.User;
import com.scheuled.barber.domain.enums.UserRole;
import com.scheuled.barber.infra.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminUserSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${api.security.admin.login}")
    private String adminLogin;

    @Value("${api.security.admin.password}")
    private String adminPassword;

    @Override
    public void run(String... args) {
        if (adminLogin == null || adminLogin.isBlank()) {
            System.err.println(">>> [SECURITY ERROR] Variável ADMIN_LOGIN não foi carregada corretamente!");
            return;
        }

        if (userRepository.findByLogin(adminLogin) == null) {
            var admin = new User(
                    null,
                    adminLogin,
                    passwordEncoder.encode(adminPassword),
                    UserRole.ADMIN
            );
            userRepository.save(admin);
            System.out.println(">>> [SECURITY] Usuário admin '" + adminLogin + "' criado com sucesso!");
        } else {
            System.out.println(">>> [SECURITY] Usuário admin '" + adminLogin + "' já existe no banco.");
        }
    }
}