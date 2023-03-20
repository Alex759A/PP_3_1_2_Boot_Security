package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.User;

import ru.kata.spring.boot_security.demo.services.RegistrationService;
import ru.kata.spring.boot_security.demo.services.UserDetailsServiceImpl;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final UserDetailsServiceImpl userDetailsService;
    private final RegistrationService registrationService;
    private final UserValidator userValidator;
    @Autowired
    public AuthController( UserDetailsServiceImpl userDetailsService, RegistrationService registrationService, UserValidator userValidator) {
        this.userDetailsService = userDetailsService;
        this.registrationService = registrationService;
        this.userValidator = userValidator;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }


    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "/auth/registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("user") @Valid User user,
                                      BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/auth/registration";
        }
        registrationService.register(user);
        return "redirect:/admin";
    }
    @GetMapping("/show")
    public String show(@ModelAttribute("user") User user, Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User u = (User) userDetailsService.loadUserByUsername(name);
        model.addAttribute("user", u);
        return "show";
    }
}











