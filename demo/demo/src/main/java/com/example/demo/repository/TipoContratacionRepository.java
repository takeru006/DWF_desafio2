package com.example.demo.repository;

import com.example.demo.model.Tipocontratacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoContratacionRepository extends JpaRepository<Tipocontratacion, Integer> {
    List<Tipocontratacion> findByTipoContratacion(String tipo);
}