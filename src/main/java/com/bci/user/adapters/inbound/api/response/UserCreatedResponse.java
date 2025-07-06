package com.bci.user.adapters.inbound.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreatedResponse {

    private String name;
    private String email;
    private List<UserPhoneResponse> phones;
}
