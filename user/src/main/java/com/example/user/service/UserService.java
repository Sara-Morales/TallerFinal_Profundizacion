package com.example.user.service;

import com.example.user.persistence.entity.User;

import java.util.List;

public interface UserService {
    void save(User user);
    void delete(User user);
    List<User> findAll();
    User findById(Long id);
}
