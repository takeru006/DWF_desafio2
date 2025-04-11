package com.example.demo.controller;

import com.example.demo.model.Contratacione;
import com.example.demo.service.ContratacionService;
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
@RequestMapping("/api/contrataciones")
public class ContratacionController {

    @Autowired
    private ContratacionService contratacionService;

    // Obtener todas las contrataciones
    @GetMapping
    public ResponseEntity<List<Contratacione>> getAllContrataciones() {
        List<Contratacione> contrataciones = contratacionService.findAll();
        return ResponseEntity.ok(contrataciones);
    }

    // Obtener una contratación por ID
    @GetMapping("/{id}")
    public ResponseEntity<Contratacione> getContratacionById(@PathVariable Integer id) {
        Optional<Contratacione> contratacionOptional = contratacionService.findById(id);

        if (contratacionOptional.isPresent()) {
            return ResponseEntity.ok(contratacionOptional.get());
        } else {
            return ResponseEntity.notFound().build(); // Devuelve un 404 si no se encuentra la contratación
        }
    }

    // Crear una nueva contratación
    @PostMapping
    public ResponseEntity<Contratacione> createContratacion(@Valid @RequestBody Contratacione contratacion) {
        Contratacione newContratacion = contratacionService.save(contratacion);
        return ResponseEntity.status(201).body(newContratacion);
    }

    // Actualizar una contratación existente
    @PutMapping("/{id}")
    public ResponseEntity<Contratacione> updateContratacion(@PathVariable Integer id, @Valid @RequestBody Contratacione contratacion) {
        contratacion.setId(id);
        Contratacione updatedContratacion = contratacionService.save(contratacion);
        return ResponseEntity.ok(updatedContratacion);
    }

    // Eliminar una contratación
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContratacion(@PathVariable Integer id) {
        contratacionService.delete(id);
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