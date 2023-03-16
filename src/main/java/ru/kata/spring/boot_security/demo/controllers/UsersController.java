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
@RequestMapping("/")
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
        System.out.println("Это метод индекс");
        return "index";
    }

    @PatchMapping("{id}")
    public String updateIndex(@ModelAttribute("user") @Valid User user,          // если ошибки, возврат на страницу редактир
                         BindingResult bindingResult,@PathVariable("id") Long id) {

        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.toString());
            return "index";
        }
        userService.update(id, user);
        System.out.println("-----------update");
        return "redirect:/";
    }


    // метод возвращающий всех людей передает всех юзеров на представление
    @GetMapping("/admin")
    public String userList(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("users", userService.findAll());
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User u = (User) userRepository.findByUserNameAndFetchRoles(name);
        model.addAttribute("user", u);

        return "admin";
    }


//     Получение одного юзера по id о передача его на представление
    @GetMapping("/admin/{idShow}")
    public String show(@PathVariable("idShow") Long id, Model model) {
        model.addAttribute("user",(User) userService.findOne(id));
        return "showUserAdmin";
    }

    @GetMapping("/admin/del{idDelete}")
    public String showDelete(@PathVariable("idDelete") Long id, Model model) {
        model.addAttribute("user", userService.findOne(id));
        return "showDelete";
    }
    // в модель -- пустого юзера
    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "newUser";
    }
    // было без new
    @PostMapping("/admin/new")
    public String createUser(@ModelAttribute("user") @Valid User user,
                             BindingResult bindingResult) { // проверка на валидность введ данных
        if (bindingResult.hasErrors())
            return "edit";
        userService.saveUser(user); // addUser(user);
        return "redirect:/admin";

    }
    // методы для обновления данных юзера  /users/3/edit  без bootstrap
    @GetMapping("/admin/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        User user = userService.findOne(id);
        model.addAttribute("user");

        return "admin";
    }

    @PatchMapping("/admin/{id}")
    public String update(@ModelAttribute("user") @Valid User user,          // если ошибки, возврат на страницу редактир
                         BindingResult bindingResult,@PathVariable("id") Long id) {
        if (bindingResult.hasErrors()) {

            return "edit";
        }

        userService.update(id, user);
        return "redirect:/admin";
    }

// метод для удаления юзера
    @DeleteMapping("/admin/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }

}
