package com.bci.user.adapters.inbound.api.request;

import com.bci.user.adapters.inbound.api.request.validation.ValidPassword;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class UserRequest {

    @NotBlank(message = "Campo name es obligatorio")
    @Size(min = 1, max = 255, message = "El campo name debe tener entre 1 y 255 caracteres")
    private String name;

    @NotBlank(message = "Campo email es obligatorio")
    @Size(min = 1, max = 255, message = "El campo email debe tener entre 1 y 255 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.cl$",
            message = "El email debe tener el formato usuario@dominio.cl")
    private String email;

    @NotBlank(message = "Campo password es obligatorio")
    @Size(min = 1, max = 255, message = "El campo password debe tener entre 1 y 255 caracteres")
    @ValidPassword
    private String password;

    @NotNull
    @NotEmpty
    private List<UserPhoneRequest> phones;
}
