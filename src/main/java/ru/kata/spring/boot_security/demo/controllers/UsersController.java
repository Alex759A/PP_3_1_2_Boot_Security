package ru.kata.spring.boot_security.demo.controllers;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.User;

import ru.kata.spring.boot_security.demo.services.UserDetailsServiceImpl;

import javax.validation.Valid;


@Controller
@RequestMapping("/admin")
public class UsersController {
    private final UserDetailsServiceImpl userService;

    @Autowired
    public UsersController(UserDetailsServiceImpl userService) {
        this.userService = userService;
    }


    // метод возвращающий всех людей передает всех юзеров на представление
    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin";
    }


//     Получение одного юзера по id о передача его на представление
    @GetMapping("/{idShow}")
    public String show(@PathVariable("idShow") Long id, Model model) {
        model.addAttribute("user",(User) userService.findOne(id));
        return "showUserAdmin";
    }

    @GetMapping("/del{idDelete}")
    public String showDelete(@PathVariable("idDelete") Long id, Model model) {
        model.addAttribute("user", userService.findOne(id));
        return "showDelete";
    }
    // в модель -- пустого юзера
    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "newUser";
    }

    @PostMapping
    public String createUser(@ModelAttribute("user") @Valid User user,
                             BindingResult bindingResult) { // проверка на валидность введ данных
        if (bindingResult.hasErrors())
            return "newUser";
        userService.saveUser(user); // addUser(user);
        return "redirect:/admin";

    }
    // методы для обновления данных юзера  /users/3/edit //
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.findOne(id));
        return "edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") @Valid User user,          // если ошибки, возврат на страницу редактир
                         BindingResult bindingResult,@PathVariable("id") Long id) {
        if (bindingResult.hasErrors())
            return "edit";
        userService.update(id, user);
        return "redirect:/admin";
    }

// метод для удаления юзера
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }

}
