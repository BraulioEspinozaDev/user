package com.bci.user.adapters.inbound.api.controller;

import com.bci.user.adapters.inbound.mapper.ApiMapper;
import com.bci.user.domain.exception.GlobalExceptionHandler;
import com.bci.user.domain.service.UserService;
import com.bci.user.util.Mocks;
import com.bci.user.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private final String PATH = "/users";
    private MockMvc mockMvc;

    @Spy
    private ApiMapper apiMapper = Mappers.getMapper(ApiMapper.class);

    @Mock
    private UserService service;

    @InjectMocks
    private UserController controller;

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
    @DisplayName("Lista todos los usuarios")
    void getAll() throws Exception {

        // Given
        BDDMockito.given(service.findAll())
                .willReturn(List.of(Mocks.getUser()));

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get(PATH)
                        .header("Authorization", "token123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
    }

    @Test
    @DisplayName("Crea un usuario")
    void create() throws Exception {

        // Given
        BDDMockito.given(service.create(ArgumentMatchers.any()))
                .willReturn(Mocks.getUser());

        // Then
        mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                        .header("Authorization", "token123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(TestUtil.jsonToString(Mocks.getUserRequest()))))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
    }
}
