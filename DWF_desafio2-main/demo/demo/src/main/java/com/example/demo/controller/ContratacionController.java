package com.example.demo.controller;

import com.example.demo.model.Contratacione;
import com.example.demo.service.ContratacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    // Obtener todas las contrataciones (ADMIN y USER)
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<List<Contratacione>> getAllContrataciones() {
        List<Contratacione> contrataciones = contratacionService.findAll();
        return ResponseEntity.ok(contrataciones);
    }

    // Obtener una contratación por ID (ADMIN y USER)
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public ResponseEntity<Contratacione> getContratacionById(@PathVariable Integer id) {
        Optional<Contratacione> contratacionOptional = contratacionService.findById(id);

        if (contratacionOptional.isPresent()) {
            return ResponseEntity.ok(contratacionOptional.get());
        } else {
            return ResponseEntity.notFound().build(); // 404 si no existe
        }
    }

    // Crear una nueva contratación (solo ADMIN)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Contratacione> createContratacion(@Valid @RequestBody Contratacione contratacion) {
        Contratacione newContratacion = contratacionService.save(contratacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(newContratacion);
    }

    // Actualizar una contratación existente (solo ADMIN)
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Contratacione> updateContratacion(@PathVariable Integer id, @Valid @RequestBody Contratacione contratacion) {
        contratacion.setId(id);
        Contratacione updatedContratacion = contratacionService.save(contratacion);
        return ResponseEntity.ok(updatedContratacion);
    }

    // Eliminar una contratación (solo ADMIN)
    @PreAuthorize("hasRole('ADMIN')")
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
