package com.bci.user.adapters.inbound.api.controller;

import com.bci.user.adapters.inbound.api.request.UserRequest;
import com.bci.user.adapters.inbound.api.response.UserResponse;
import com.bci.user.adapters.inbound.mapper.ApiMapper;
import com.bci.user.domain.model.User;
import com.bci.user.domain.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Usuarios", description = "Operaciones relacionadas con usuarios")
public class UserController {

    private final ApiMapper mapper;
    private final UserService userService;

    @GetMapping
    @Operation(summary = "Obtener listado de usuarios", description = "Devuelve listado de usuarios")
    public ResponseEntity<List<User>> getAll(){
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Crea un usuario", description = "Crea un usuario y sus teléfonos")
    public ResponseEntity<UserResponse> create(
            @Validated(PostMapping.class)
            @RequestBody @Valid UserRequest request,
            @RequestHeader("Authorization") String token){
        return new ResponseEntity<>(mapper.toResponse(
                userService.create(mapper.toDomain(request, token))), HttpStatus.CREATED);
    }
}
