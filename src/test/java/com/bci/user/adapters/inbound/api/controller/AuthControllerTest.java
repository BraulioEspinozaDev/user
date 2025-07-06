package com.bci.user.adapters.inbound.api.controller;

import com.bci.user.domain.exception.GlobalExceptionHandler;
import com.bci.user.infraestructure.config.JwtUtil;
import com.bci.user.util.Mocks;
import com.bci.user.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    private MockMvc mockMvc;
    private static final String PATH = "/auth";

    @Mock
    private Authentication authentication;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthController controller;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(GlobalExceptionHandler.class)
                .alwaysDo(MockMvcResultHandlers.print())
                .setMessageConverters(TestUtil.getMessageConverters())
                .build();
    }

    @Test
    @DisplayName("Realiza un login")
    void login() throws Exception {

        // Given
        BDDMockito.given(authenticationManager.authenticate(ArgumentMatchers.any()))
                .willReturn(authentication);

        BDDMockito.given(jwtUtil.generateToken(ArgumentMatchers.anyString()))
                .willReturn("user");

        BDDMockito.given(jwtUtil.getExpirationDateFromToken(ArgumentMatchers.anyString()))
                .willReturn(new Date());

        // Then
        mockMvc.perform(MockMvcRequestBuilders.post(PATH + "/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(TestUtil.jsonToString(Mocks.getLoginRequest()))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
    }

    @Test
    @DisplayName("Realiza un logout")
    void logout() throws Exception {

        // Given
        BDDMockito.given(jwtUtil.getUsernameFromToken(ArgumentMatchers.anyString()))
                .willReturn("user");

        // Then
        mockMvc.perform(MockMvcRequestBuilders.post(PATH + "/logout")
                        .header("Authorization", "token123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
    }

    @Test
    @DisplayName("Valida un token")
    void validateToken() throws Exception {

        // Given
        BDDMockito.given(jwtUtil.validateToken(ArgumentMatchers.anyString()))
                .willReturn(true);

        BDDMockito.given(jwtUtil.isTokenExpired(ArgumentMatchers.anyString()))
                .willReturn(false);

        BDDMockito.given(jwtUtil.getUsernameFromToken(ArgumentMatchers.anyString()))
                .willReturn("user");

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get(PATH + "/validate")
                        .header("Authorization", "token123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
    }
}
