package com.bci.user.domain.service;

import com.bci.user.domain.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();
    User create(User user);
}
