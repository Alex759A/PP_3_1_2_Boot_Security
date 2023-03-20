package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.services.UserDetailsServiceImpl;

import javax.validation.Valid;
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserDetailsServiceImpl userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    public AdminController(UserDetailsServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping
    public String userList(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("users", userService.findAll());
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User u = (User) userRepository.findByUserNameAndFetchRoles(name);
        model.addAttribute("user", u);

        return "admin";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "newUser";
    }

    @PostMapping("/new")
    public String createUser(@ModelAttribute("user") @Valid User user,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "newUser";
        userService.saveUser(user);
        return "redirect:/admin";

    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult,@PathVariable("id") Long id) {
        if (bindingResult.hasErrors()) {
            return "edit";
        }
        userService.update(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
