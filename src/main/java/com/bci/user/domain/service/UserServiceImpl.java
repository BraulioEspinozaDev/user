package com.bci.user.domain.service;

import com.bci.user.domain.exception.CreateUserException;
import com.bci.user.domain.exception.EmailException;
import com.bci.user.domain.exception.FindAllUserException;
import com.bci.user.domain.model.User;
import com.bci.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UserRepository userRepository;

    /**
     * Recupera todos los usuarios del sistema.
     *
     * @return Lista de todos los usuarios registrados en el sistema
     * @throws FindAllUserException si ocurre un error durante la consulta a la base de datos
     */
    @Override
    public List<User> findAll() {
        try{
            return userRepository.findAll();
        } catch (Exception e){
            log.error("Error al obtener usuarios: {}", e.getMessage());
            throw new FindAllUserException();
        }
    }


    /**
     * Crea un nuevo usuario en el sistema.
     *
     * Este método valida que el email del usuario no exista previamente,
     * encripta la contraseña, procesa el token y establece el estado activo
     * del usuario antes de guardarlo en la base de datos.
     *
     * @param user El objeto User a crear con todos los datos necesarios
     * @return El usuario creado con sus datos actualizados (ID generado, contraseña encriptada, etc.)
     * @throws EmailException si ya existe un usuario con el mismo email
     * @throws CreateUserException si ocurre un error durante el proceso de creación
     */
    @Override
    @Transactional
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
            throw new CreateUserException();
        }
    }

}
