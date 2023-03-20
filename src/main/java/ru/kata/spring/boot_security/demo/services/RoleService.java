package ru.kata.spring.boot_security.demo.services;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;

import java.util.List;

public interface RoleService  {
    Role addRole(Role role);
    void delete(long id);
    Role getByName(String name);
    public Role getById(long id);
    Role editBank(Role role);
    List<Role> getAll();



}
