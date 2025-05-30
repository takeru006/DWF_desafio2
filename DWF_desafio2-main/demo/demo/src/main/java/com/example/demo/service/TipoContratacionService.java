package com.example.demo.service;

import com.example.demo.model.Tipocontratacion;
import com.example.demo.repository.TipoContratacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoContratacionService {

    @Autowired
    private TipoContratacionRepository tipoContratacionRepository;

    public List<Tipocontratacion> findAll() {
        return tipoContratacionRepository.findAll();
    }

    public Optional<Tipocontratacion> findById(Integer id) {
        return tipoContratacionRepository.findById(id);
    }

    public Tipocontratacion save(Tipocontratacion tipoContratacion) {
        return tipoContratacionRepository.save(tipoContratacion);
    }

    public Tipocontratacion update(Integer id, Tipocontratacion tipoContratacion) {
        tipoContratacion.setId(id);
        return tipoContratacionRepository.save(tipoContratacion);
    }

    public void delete(Integer id) {
        tipoContratacionRepository.deleteById(id);
    }
}