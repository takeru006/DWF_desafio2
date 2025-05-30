package com.example.demo.service;

import com.example.demo.model.Cargo;
import com.example.demo.repository.CargoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CargoService {

    @Autowired
    private CargoRepository cargoRepository;

    public List<Cargo> findAll() {
        return cargoRepository.findAll();
    }

    public Optional<Cargo> findById(Integer id) {
        return cargoRepository.findById(id);
    }

    public Cargo save(Cargo cargo) {
        return cargoRepository.save(cargo);
    }

    public Cargo update(Integer id, Cargo cargo) {
        cargo.setId(id);
        return cargoRepository.save(cargo);
    }

    public void delete(Integer id) {
        cargoRepository.deleteById(id);
    }
}