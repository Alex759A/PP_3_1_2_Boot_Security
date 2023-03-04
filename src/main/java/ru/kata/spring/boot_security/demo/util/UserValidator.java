package ru.kata.spring.boot_security.demo.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.UserDetailsServiceImpl;
@Component
public class UserValidator implements Validator {
    private final UserDetailsServiceImpl userDetailsService;
    @Autowired
    public UserValidator(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
    // указывает на класс которому нужен валидатор
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }
    // проверка в БД на наличие-отсутствие человека
    // если искл выбросилось, значит человек не найден в таблице
    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        try {
            userDetailsService.loadUserByUsername(user.getUsername());
        } catch (UsernameNotFoundException ignored) {
            return; // пользователь не найден
        }
        errors.rejectValue("username", "", "Пользователь с таким именем--существует");
    }
}
