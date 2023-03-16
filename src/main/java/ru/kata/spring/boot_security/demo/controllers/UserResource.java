//package ru.kata.spring.boot_security.demo.controllers;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//import ru.kata.spring.boot_security.demo.entities.User;
//import ru.kata.spring.boot_security.demo.services.UserDetailsServiceImpl;
//
//import javax.validation.Valid;
//
//@RestController
//@RequestMapping("api/vi/user")
//public class UserResource {
//
//    private final UserDetailsServiceImpl userService;
//    @Autowired
//    public UserResource(UserDetailsServiceImpl userService) {
//        this.userService = userService;
//    }
//    //     Получение одного юзера по id о передача его на представление
//    @GetMapping("/{idShow}")
//    public String show(@PathVariable("idShow") Long id, Model model) {
//        model.addAttribute("user",(User) userService.findOne(id));
//        return "showUserAdmin";
//    }
//
////    @PostMapping
////    public User create(@RequestBody User user) {
////        return userService.save(user);
////    }
//    // в модель -- пустого юзера
//    @GetMapping("/new")
//    public String newUser(@ModelAttribute("user") User user) {
//        return "newUser";
//    }
//
//    @PostMapping
//    public String createUser(@ModelAttribute("user") @Valid User user,
//                             BindingResult bindingResult) { // проверка на валидность введ данных
//        if (bindingResult.hasErrors())
//            return "newUser";
//        userService.saveUser(user); // addUser(user);
//        return "redirect:/admin";
//
//    }
//
//}

