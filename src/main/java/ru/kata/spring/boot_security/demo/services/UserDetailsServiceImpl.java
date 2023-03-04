package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import java.util.Optional;



// сервис загружает пользователя
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @PersistenceContext
    private EntityManager em;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userNameLogin = userRepository.findByUsername(username);

        if (userNameLogin == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return userNameLogin;
    }

    public User findOne(Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());
        if (userFromDB != null) {
            return false;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton(new Role(3L, "ROLE_USER")));
        userRepository.save(user);
        return true;
    }
    @Transactional
    public void update(Long id, User updateUser) {
        updateUser.setId(id);
        updateUser.setPassword(passwordEncoder.encode(updateUser.getPassword()));
        updateUser.setRoles(Collections.singleton(new Role(3L, "ROLE_USER")));
        userRepository.save(updateUser);
    }

    @Transactional
    public boolean delete(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

}





//    @Override
//    public void updateUser(Long userId, User updateUser) {
//        User userToBeUpdate = userRepository.getById(userId);
//        userToBeUpdate.setUsername(updateUser.getUsername());
//        userToBeUpdate.setPassword(bCryptPasswordEncoder.encode(updateUser.getPassword()));
//        userToBeUpdate.setRoles((Set<Role>) updateUser.getRoles());
//        userRepository.save(userToBeUpdate);
////        userToBeUpdate.setPassword(bCryptPasswordEncoder.encode(updateUser.getPassword()));
////        userToBeUpdate.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
//    }




    //    @Override
//    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//        User person = userRepository.findByUsername(s);
//
//        if (person.isEmpty())
//            throw new UsernameNotFoundException("User not found");
//
//        return new UserDetails(user.get());
//    }


//        return new org.springframework.security.core.userdetails.User(user.getFirstName(), user.getLastName(),
//                mapRolesAutorities(user.getRoles()));
//    }

//    @Override
//    @Transactional
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
//        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
//    }    @Override
//    @Transactional
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
//        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
//    }

//    // метод из пачки ролей делает пачку авторитис
//    private Collection<? extends GrantedAuthority> mapRolesAutorities(Collection<Role> roles) {
//        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getRole())).collect(Collectors.toList());
//    }
//}
