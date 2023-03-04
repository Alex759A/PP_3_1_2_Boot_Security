package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.services.RegistrationService;
import ru.kata.spring.boot_security.demo.services.UserDetailsServiceImpl;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final UserDetailsServiceImpl userDetailsService;
    private final RegistrationService registrationService;
    private final UserValidator userValidator;
    @Autowired
    public AuthController(UserRepository userRepository, UserDetailsServiceImpl userDetailsService, RegistrationService registrationService, UserValidator userValidator) {
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
        this.registrationService = registrationService;
        this.userValidator = userValidator;
    }

    // метод обработчик аутентификации
    // .loginProcessingUrl("/process_login") // своб.адрес--обрабатывает спринг
    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    // методы обработки регистрации польз...(в модель пустой юзер (@ModelAttribute--позволяет это сделать))
    @GetMapping("/registration")
//    public String registrationPage(@ModelAttribute("user") User user) {
    public String registration(Model model) {
        System.out.println("Это метод регистрации");
        model.addAttribute("user", new User());
        return "/auth/registration";
    }
//     обработчик пришедших данных с формы регистрации
    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("user") @Valid User user,
                                      BindingResult bindingResult) {
        System.out.println("Мет од перед валидацией");
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) { /// если есть ошибки -- на стр регистрации не сохраняя
            return "/auth/registration";
        }
        System.out.println("Metod performRegistration");
        registrationService.register(user); // вызов своего метода регистрации
        System.out.println("Metod performRegistration2");
        return "redirect:/";
    }
    @GetMapping("/show")
    public String show(@ModelAttribute("user") User user, Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User u = (User) userRepository.findByUsername(name);
        model.addAttribute("user", u);
        return "show";
    }

}










////    @PostMapping //("/registration")
////    public String addUser(@ModelAttribute("userForm") @Valid User userForm, BindingResult bindingResult, Model model) {
////
////        if (bindingResult.hasErrors()) {
////            return "registration";
////        }
////        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())){
////            model.addAttribute("passwordError", "Пароли не совпадают");
////            return "registration";
////        }
////        if (!userService.saveUser(userForm)){
////            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
////            return "registration";
////        }
////
////        return "redirect:/";
////    }
