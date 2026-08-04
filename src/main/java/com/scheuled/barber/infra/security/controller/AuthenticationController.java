package com.scheuled.barber.infra.security.controller;

import com.scheuled.barber.domain.entity.User;
import com.scheuled.barber.infra.security.TokenService;
import com.scheuled.barber.infra.security.dto.AuthenticationRequestData;
import com.scheuled.barber.infra.security.dto.TokenJwtResponseData;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<TokenJwtResponseData> login(@RequestBody @Valid AuthenticationRequestData data) {
        // 1. Cria o token de autenticação interno do Spring
        var authenticationToken = new UsernamePasswordAuthenticationToken(data.login(), data.password());

        // 2. O AuthenticationManager valida login + senha via UserDetailsService e BCrypt
        var authentication = manager.authenticate(authenticationToken);

        // 3. Gera o Token JWT para o usuário autenticado
        var tokenJWT = tokenService.generateToken((User) authentication.getPrincipal());

        // 4. Retorna HTTP 200 OK com o Token JWT envelopado
        return ResponseEntity.ok(new TokenJwtResponseData(tokenJWT));
    }
}