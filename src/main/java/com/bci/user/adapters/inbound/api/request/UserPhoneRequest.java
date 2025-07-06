package com.bci.user.adapters.inbound.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserPhoneRequest {

    @NotBlank(message = "Campo number es obligatorio")
    private String number;

    @NotBlank(message = "Campo cityCode es obligatorio")
    @JsonProperty("citycode")
    private String cityCode;

    @NotBlank(message = "Campo contryCode es obligatorio")
    @JsonProperty("contrycode")
    private String contryCode;
}
