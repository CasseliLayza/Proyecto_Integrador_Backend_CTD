package com.backend.proyectointegradorc1g6.repository;

import com.backend.proyectointegradorc1g6.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<Login, Long> {
}
