package com.example.crud_mysql_jwt.repositories;

import com.example.crud_mysql_jwt.models.Entertainment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntertainmentRepository extends JpaRepository<Entertainment, Long> {
}
