package com.scheuled.barber.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // 1. Desabilita CSRF pois a API é Stateless e usa Tokens Bearer no Header
                .csrf(csrf -> csrf.disable())

                // 2. Define o gerenciamento de sessão como STATELESS (sem guardar estado no servidor)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 3. Regras de autorização por endpoint
                .authorizeHttpRequests(req -> {
                    // Endpoint público para autenticação
                    req.requestMatchers(HttpMethod.POST, "/login").permitAll();

                    // Endpoint público para consulta de disponibilidade (útil para o Agente de IA consultar agenda livre)
                    req.requestMatchers(HttpMethod.GET, "/appointments/availability").permitAll();

                    // Qualquer outra requisição (criar agendamento, cadastrar barbeiro, etc.) exige Token JWT
                    req.anyRequest().authenticated();
                })

                // 4. Registra nosso filtro customizado ANTES do filtro padrão do Spring Security
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Algoritmo forte de hashing de senhas (salt automático + rounds de custo)
        return new BCryptPasswordEncoder();
    }
}