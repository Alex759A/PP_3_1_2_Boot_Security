package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.User;

import ru.kata.spring.boot_security.demo.repositories.RoleRepository;

import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.services.UserDetailsServiceImpl;

import javax.validation.Valid;



@Controller
@RequestMapping("/user")
public class UsersController {
    private final UserDetailsServiceImpl userService;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;


    @Autowired
    public UsersController(UserDetailsServiceImpl userService) {
        this.userService = userService;
    }


    @GetMapping
    public String showIndex( Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User u = (User) userService.loadUserByUsername(name);
        model.addAttribute("user", u);

        return "user";
    }

    @PatchMapping("{id}")
    public String updateIndex(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult,@PathVariable("id") Long id) {

        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult);
            return "user";
        }
        userService.update(id, user);
        return "redirect:/user";
    }

}
