package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;

import ru.kata.spring.boot_security.demo.repositories.UserRepository;


import java.util.*;



@Service
public class UserDetailsServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleServiceImpl roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userNameLogin = userRepository.findByUserNameAndFetchRoles(username);
        if (userNameLogin == null) {
            throw new UsernameNotFoundException("User not found");
        }
            return userNameLogin;
    }
    @Override
    public User findOne(Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
    }
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByUserNameAndFetchRoles(user.getUsername());
        if (userFromDB != null) {
            return false;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(user.getRoles());
        userRepository.save(user);
        return true;
    }

    @Transactional
    @Override
    public void update(Long id, User updateUser) {
        User updateNewUser = userRepository.findById(id).get();

        Set<Role> roles = updateNewUser.getRoles();
        for (Role x : updateUser.getRoles()) {
            long z = x.getId();
            roles.add(roleService.getById(z));
        }
        updateNewUser.setRoles(roles);


        updateNewUser.setFirstName(updateUser.getFirstName());
        updateNewUser.setLastName(updateUser.getLastName());
        updateNewUser.setAge(updateUser.getAge());
        updateNewUser.setEmail(updateUser.getEmail());

        userRepository.save(updateNewUser);
    }

    @Transactional
    @Override
    public boolean delete(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }


}




