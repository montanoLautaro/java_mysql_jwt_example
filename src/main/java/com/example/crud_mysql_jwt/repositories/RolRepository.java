package com.example.crud_mysql_jwt.repositories;

import com.example.crud_mysql_jwt.models.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol,Long> {

    public Optional<Rol> findByName(String name);
}
