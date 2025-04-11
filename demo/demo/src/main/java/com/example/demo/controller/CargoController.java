package com.example.demo.controller;

import com.example.demo.model.Cargo;
import com.example.demo.service.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/cargos")
public class CargoController {

    @Autowired
    private CargoService cargoService;

    // Obtener todos los cargos
    @GetMapping
    public ResponseEntity<List<Cargo>> getAllCargos() {
        List<Cargo> cargos = cargoService.findAll();
        return ResponseEntity.ok(cargos);
    }

    // Obtener un cargo por ID
    @GetMapping("/{id}")
    public ResponseEntity<Cargo> getCargoById(@PathVariable Integer id) {
        Optional<Cargo> cargoOptional = cargoService.findById(id);

        if (cargoOptional.isPresent()) {
            return ResponseEntity.ok(cargoOptional.get());
        } else {
            return ResponseEntity.notFound().build(); // Devuelve un 404 si no se encuentra el cargo
        }
    }

    // Crear un nuevo cargo
    @PostMapping
    public ResponseEntity<Cargo> createCargo(@Valid @RequestBody Cargo cargo) {
        Cargo newCargo = cargoService.save(cargo);
        return ResponseEntity.status(201).body(newCargo);
    }

    // Actualizar un cargo existente
    @PutMapping("/{id}")
    public ResponseEntity<Cargo> updateCargo(@PathVariable Integer id, @Valid @RequestBody Cargo cargo) {
        cargo.setId(id);
        Cargo updatedCargo = cargoService.save(cargo);
        return ResponseEntity.ok(updatedCargo);
    }

    // Eliminar un cargo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCargo(@PathVariable Integer id) {
        cargoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Manejo de errores de validación
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}