package ru.kata.spring.boot_security.demo.entities;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;

import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name="users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="username")
    @NotEmpty(message = "Name should note be empty!")
    @Size(min = 2, max = 30, message = "Name should be betwen 2 and 30 characters")
    private String username;

    @Transient
    private String passwordConfirm;
    @Column(name="password")
    private String password;

    @Column(name = "name")
    @NotEmpty(message = "Name should note be empty!")
    @Size(min = 2, max = 30, message = "Name should be betwen 2 and 30 characters")
    private String firstName;



    @Column(name = "last_name")
    @NotEmpty(message = "Name should note be empty!")
    @Size(min = 2, max = 30, message = "Name should be betwen 2 and 30 characters")
    private String lastName;

    @Column(name = "email")
    @NotEmpty(message = "Email should note be empty!")
    @Email(message = "Email should be valid!")
    private String email;

    @Column(name="age")
    private Long age;

    public User() {
    }

    public User(String firstName, String lastName, String email, Long age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;

    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
//        return (Collection<? extends GrantedAuthority> (List) getRoles());
    }


    @ManyToMany
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

 //   public Collection<? extends GrantedAuthority> mapRolesAutorities(Set<Role> roles) {
//        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }
    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }
    public Set <Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }
}
