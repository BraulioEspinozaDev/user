package com.bci.user.domain.service;

import com.bci.user.domain.exception.EmailException;
import com.bci.user.domain.model.User;
import com.bci.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User create(User user) {
        Optional<User> userSameEmail = userRepository.findByEmail(user.getEmail());
        if(userSameEmail.isPresent()) {
            throw new EmailException();
        }

        try{
            user.setActive(true);
            user.setToken(user.getToken().substring(7));
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.getPhones().forEach(phone -> phone.setUser(user));
            return userRepository.save(user);
        } catch (Exception ex) {
            log.error("Error al crear usuario: {}", ex.getMessage());
            return null;
        }
    }

}
