package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao {
    List<User> getAllUsers();
    User getById(Integer id);
    User saveUser(User user);
    void deleteUser(Integer id);
    void updateUser(User user);
    User findByUsername(String email);
}
