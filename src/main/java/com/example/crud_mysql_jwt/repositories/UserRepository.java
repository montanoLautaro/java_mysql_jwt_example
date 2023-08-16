package com.example.crud_mysql_jwt.repositories;

import com.example.crud_mysql_jwt.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<User, Long> {
    public Optional<User> findByEmail(String email);

    public Boolean existsByEmail(String email);
}
