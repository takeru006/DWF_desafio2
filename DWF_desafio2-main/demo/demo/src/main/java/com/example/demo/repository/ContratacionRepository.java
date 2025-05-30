package com.example.demo.repository;

import com.example.demo.model.Contratacione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContratacionRepository extends JpaRepository<Contratacione, Integer> {
    List<Contratacione> findByEmpleadoId(Integer empleadoId);
}