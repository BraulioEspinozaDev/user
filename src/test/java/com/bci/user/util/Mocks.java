package com.bci.user.util;

import com.bci.user.adapters.inbound.api.request.LoginRequest;
import com.bci.user.adapters.inbound.api.request.UserPhoneRequest;
import com.bci.user.adapters.inbound.api.request.UserRequest;
import com.bci.user.adapters.inbound.api.response.LoginResponse;
import com.bci.user.domain.model.User;
import com.bci.user.domain.model.UserPhone;

import java.util.List;
import java.util.UUID;

public class Mocks {

    public static LoginRequest getLoginRequest() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("admin");
        loginRequest.setPassword("password");
        return loginRequest;
    }

    public static User getUser(){
        return User.builder()
                .id(1L)
                .name("Juan")
                .active(true)
                .email("juan@gmail.cl")
                .uuid(UUID.randomUUID())
                .token("addfsa1125af77g9hhh7we")
                .password("password")
                .phones(List.of(getUserPhone()))
                .build();
    }

    public static UserPhone getUserPhone(){
        return UserPhone.builder()
                .id(1L)
                .cityCode("111")
                .contryCode("45")
                .number("11123")
                .build();
    }

    public static UserRequest getUserRequest(){
        UserRequest userRequest = new UserRequest();
        userRequest.setName("carlos");
        userRequest.setPassword("password");
        userRequest.setEmail("carlos@gmail.cl");
        userRequest.setPhones(List.of(getUserPhoneRequest()));
        return userRequest;
    }

    public static UserPhoneRequest getUserPhoneRequest(){
        UserPhoneRequest userPhoneRequest = new UserPhoneRequest();
        userPhoneRequest.setCityCode("111");
        userPhoneRequest.setNumber("111123");
        userPhoneRequest.setContryCode("23");
        return userPhoneRequest;
    }
}
