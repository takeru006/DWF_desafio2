package com.example.demo.service;

import com.example.demo.model.Contratacione;
import com.example.demo.repository.ContratacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContratacionService {

    @Autowired
    private ContratacionRepository contratacionRepository;

    public List<Contratacione> findAll() {
        return contratacionRepository.findAll();
    }

    public Optional<Contratacione> findById(Integer id) {
        return contratacionRepository.findById(id);
    }

    public Contratacione save(Contratacione contratacion) {
        return contratacionRepository.save(contratacion);
    }

    public Contratacione update(Integer id, Contratacione contratacion) {
        contratacion.setId(id);
        return contratacionRepository.save(contratacion);
    }

    public void delete(Integer id) {
        contratacionRepository.deleteById(id);
    }
}