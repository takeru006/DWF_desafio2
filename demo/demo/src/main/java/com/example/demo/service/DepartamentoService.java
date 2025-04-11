package com.example.demo.service;

import com.example.demo.model.Departamento;
import com.example.demo.repository.DepartamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartamentoService {

    @Autowired
    private DepartamentoRepository departamentoRepository;

    public List<Departamento> findAll() {
        return departamentoRepository.findAll();
    }

    public Optional<Departamento> findById(Integer id) {
        return departamentoRepository.findById(id);
    }

    public Departamento save(Departamento departamento) {
        return departamentoRepository.save(departamento);
    }

    public Departamento update(Integer id, Departamento departamento) {
        departamento.setId(id);
        return departamentoRepository.save(departamento);
    }

    public void delete(Integer id) {
        departamentoRepository.deleteById(id);
    }
}