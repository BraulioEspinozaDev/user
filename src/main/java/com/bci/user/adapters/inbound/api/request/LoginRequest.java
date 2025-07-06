package com.bci.user.adapters.inbound.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "Username is requerido")
    @Size(min = 3, max = 50, message = "Username debe tener entre 3 y 50 caracteres")
    private String username;

    @NotBlank(message = "Password is requerido")
    @Size(min = 6, max = 100, message = "Password debe tener entre 6 y 100 caracteres")
    private String password;
}
