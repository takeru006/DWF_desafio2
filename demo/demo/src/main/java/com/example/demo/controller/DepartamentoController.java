package com.example.demo.controller;

import com.example.demo.model.Departamento;
import com.example.demo.service.DepartamentoService;
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
@RequestMapping("/api/departamentos")
public class DepartamentoController {

    @Autowired
    private DepartamentoService departamentoService;

    // Obtener todos los departamentos
    @GetMapping
    public ResponseEntity<List<Departamento>> getAllDepartamentos() {
        List<Departamento> departamentos = departamentoService.findAll();
        return ResponseEntity.ok(departamentos);
    }

    // Obtener un departamento por ID
    @GetMapping("/{id}")
    public ResponseEntity<Departamento> getDepartamentoById(@PathVariable Integer id) {
        Optional<Departamento> departamentoOptional = departamentoService.findById(id);

        if (departamentoOptional.isPresent()) {
            return ResponseEntity.ok(departamentoOptional.get());
        } else {
            return ResponseEntity.notFound().build(); // Devuelve un 404 si no se encuentra el departamento
        }
    }

    // Crear un nuevo departamento
    @PostMapping
    public ResponseEntity<Departamento> createDepartamento(@Valid @RequestBody Departamento departamento) {
        Departamento newDepartamento = departamentoService.save(departamento);
        return ResponseEntity.status(201).body(newDepartamento);
    }

    // Actualizar un departamento existente
    @PutMapping("/{id}")
    public ResponseEntity<Departamento> updateDepartamento(@PathVariable Integer id, @Valid @RequestBody Departamento departamento) {
        departamento.setId(id);
        Departamento updatedDepartamento = departamentoService.save(departamento);
        return ResponseEntity.ok(updatedDepartamento);
    }

    // Eliminar un departamento
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartamento(@PathVariable Integer id) {
        departamentoService.delete(id);
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