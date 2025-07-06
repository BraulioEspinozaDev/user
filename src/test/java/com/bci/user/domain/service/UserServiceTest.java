package com.bci.user.domain.service;

import com.bci.user.domain.model.User;
import com.bci.user.domain.repository.UserRepository;
import com.bci.user.util.Mocks;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserServiceImpl service;

    @Test
    @DisplayName("Obtiene listado de usuarios")
    void findAll() {

        // Given
        BDDMockito.given(repository.findAll())
                .willReturn(List.of(Mocks.getUser()));

        // When
        List<User> result = service.findAll();

        // Then
        BDDAssertions.then(result).isNotNull();
        BDDAssertions.then(result).isNotEmpty();
    }

    @Test
    @DisplayName("Crea usuario exitosamente")
    void create() {

        // Given
        BDDMockito.given(repository.findByEmail(ArgumentMatchers.anyString()))
                .willReturn(Optional.empty());

        BDDMockito.given(repository.save(ArgumentMatchers.any()))
                .willReturn(Mocks.getUser());

        // When
        User result = service.create(Mocks.getUser());

        // Then
        BDDAssertions.then(result).isNotNull();
    }
}
