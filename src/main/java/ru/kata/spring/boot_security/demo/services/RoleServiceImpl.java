package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;

import java.util.List;
@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public Role addRole(Role role) {
        return roleRepository.saveAndFlush(role);
    }

    @Override
    public void delete(long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public Role getByName(String name) {
        return roleRepository.findByName(name);

    }
    public Role getById(long id) {
        return roleRepository.getById(id);
    }

    @Override
    public Role editBank(Role role) {
        return roleRepository.saveAndFlush(role);
    }

    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }
}
