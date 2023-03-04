package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


// класс-настройка Security
@Configuration
@EnableWebSecurity // включение секьюрности(включает в себя @Component )
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private SuccessUserHandler successUserHandler;

    @Autowired
    public WebSecurityConfig(SuccessUserHandler successUserHandler) {
        this.successUserHandler = successUserHandler;
    }


    // Конфигурация Spring Security и конфигур. авторизации
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http       //.csrf().disable()               //.csrf().disable() // защита от межсайтовой подделки запросов
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/auth/show").hasAnyRole("USER", "ADMIN")
                .antMatchers("/auth/login", "/auth/registration", "/error", "/index").permitAll() // доступ всем
                .anyRequest().authenticated()             // далее только аутентифиц польз
                .and()
                .formLogin()                                // переопределение формы аутент
                .loginPage("/auth/login")                  // адрес для формы аутенти.
                .loginProcessingUrl("/process_login")      // своб.адрес--обрабатывает спринг
                .defaultSuccessUrl("/", true)
                .successHandler(successUserHandler)  // успех
                .failureUrl("/auth/login?error")    // не успех
                .and()
                .logout()
                .logoutUrl("/logout")              //-- ссылка для логаута -- кнопка
                .logoutSuccessUrl("/auth/login");           //-- переброс после успешного разлога

    }

    //   .logout().logoutUrl("/logout)-- ссылка для логаута -- кнопка
//    .logoutSuccessUrl("/auth/login") -- переброс послк успешного разлога
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}






//    @Override
//    @Autowired
//    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
//    }

    // аутентификация inMemory 11
//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
//        UserDetails user =
//                User.withDefaultPasswordEncoder()
//                        .username("user")
//                        .password("user")
//                        .roles("USER")
//                        .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }


// настройка авторизации authorizeRequests() защита блоков приложения
//                .antMatchers("/admin/**").hasRole("ADMIN", "SUPERADMIN")  // на опр.страницу только с указанными ролями
//                .antMatchers("/authenticated/**").authenticated() // если от корня нач. с /authenticated -- то только аутентифицированные пользователи
//                .and() // разделитель
//                .httpBasic() или formLogin() // если не аутентиф--то на форму аутонтификации(сдесь можно встав свою форму,
//                // если нет то форма поумолч.
//                .and()
//                .logout().logoutSuccessUrl("/")// возврат в корень
//                .formLogin().successHandler(successUserHandler)// обработчик успешеной аутент.-- сюда вешаем свой  successUserHandler               .antMatchers("/profile/**").authenticated() // в профиль только с учеткой
// в хэндлере обработка зашедшего пользователя

                /////////////////////////////////////////////////////////// нач настройки
//                .anyRequest().authenticated()
//                .and()  // разделитель настроек
//                .formLogin().successHandler(successUserHandler)
//                .loginProcessingUrl("/svoiadreslogin")
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll();
//    }


//    // аутентификация inMemory 11
//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
//        UserDetails user =
//                User.withDefaultPasswordEncoder()
//                        .username("user")
//                        .password("user")
//                        .roles("USER")
//                        .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }
    // назначение passwordEncoder
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public DaoAuthenticationProvider daoAuthenticationProvider() {
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder()); // указание на свой passwordEncoder
//        daoAuthenticationProvider.setUserDetailsService(userService);
//        return daoAuthenticationProvider;
//    }

    // jdbsAuthentication 22
//    public JdbcUserDetailsManager users(DataSource dataSource) {
//        // загрузка наших пользователей в бд
//
//                UserDetails user = User.builder()
//                .username("name")
//                .password("{bcrypt}$2a$12$OS7FVONLjxd3tnFCMtM2se6jIOA3ESE3KTAzrdRwiH.k7IrhSmzEW") // bcrypt - generator.com
//                .roles("USER") // роли
//                .build(); // в конце сборка
//
//        UserDetails admin = User.builder() // сборка -- admin
//                .username("admin")