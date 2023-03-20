package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("select b from Role b where b.name = :name")
    Role findByName(@Param("name") String name);

}
