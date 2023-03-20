package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.entities.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    public UserDetails loadUserByUsername(String username);

    public User findOne(Long userId);

    public List<User> findAll();
    public boolean saveUser(User user);
    public void update(Long id, User updateUser);

    public boolean delete(Long userId);
}
