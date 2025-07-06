package com.bci.user.adapters.inbound.api.controller;

import com.bci.user.adapters.inbound.api.request.LoginRequest;
import com.bci.user.adapters.inbound.api.response.LoginResponse;
import com.bci.user.infraestructure.config.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "auth", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Autenticación", description = "Operaciones relacionadas a login y token")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("login")
    @Operation(summary = "Realiza login", description = "Usuario y password")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            log.info("Intento de login para: {}", loginRequest.getUsername());

            // Autenticar usuario
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            // Generar token JWT
            String token = jwtUtil.generateToken(loginRequest.getUsername());

            // Calcular fecha de expiración
            Date expirationDate = jwtUtil.getExpirationDateFromToken(token);
            LocalDateTime expiresAt = expirationDate.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();

            LoginResponse response = new LoginResponse(token, loginRequest.getUsername(), expiresAt);

            log.info("Login exitoso para : {}", loginRequest.getUsername());
            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            log.warn("Credenciales inválidas para : {}", loginRequest.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse(null, null, null, null, "Invalid credentials"));

        } catch (AuthenticationException e) {
            log.error("Error de autenticación para : {}", loginRequest.getUsername(), e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse(null, null, null, null, "Authentication failed"));

        } catch (Exception e) {
            log.error("Error inesperado en login para: {}", loginRequest.getUsername(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new LoginResponse(null, null, null, null, "Internal server error"));
        }
    }

    @PostMapping("logout")
    @Operation(summary = "Realiza logout", description = "Cierra sesión")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        try {
            // Extraer token sin "Bearer "
            String jwtToken = token.substring(7);
            String username = jwtUtil.getUsernameFromToken(jwtToken);

            log.info("Logout exitoso para: {}", username);
            return ResponseEntity.ok("Logout successful");

        } catch (Exception e) {
            log.error("Error durante logout", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Logout fallido");
        }
    }

    @GetMapping("validate")
    @Operation(summary = "Valida Token", description = "Validación de token")
    public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String token) {
        try {
            String jwtToken = token.substring(7);

            if (jwtUtil.validateToken(jwtToken) && !jwtUtil.isTokenExpired(jwtToken)) {
                String username = jwtUtil.getUsernameFromToken(jwtToken);
                return ResponseEntity.ok("Token es valido para: " + username);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Token es inválido o expiró");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Validación de token fallida");
        }
    }
}
