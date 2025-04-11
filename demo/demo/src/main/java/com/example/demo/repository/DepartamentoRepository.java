package com.example.demo.repository;

import com.example.demo.model.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento, Integer> {
    List<Departamento> findByNombreDepartamento(String nombre);
}