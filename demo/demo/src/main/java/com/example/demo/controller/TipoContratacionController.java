package com.example.demo.controller;

import com.example.demo.model.Tipocontratacion;
import com.example.demo.service.TipoContratacionService;
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
@RequestMapping("/api/tipos-contratacion")
public class TipoContratacionController {

    @Autowired
    private TipoContratacionService tipoContratacionService;

    // Obtener todos los tipos de contratación
    @GetMapping
    public ResponseEntity<List<Tipocontratacion>> getAllTiposContratacion() {
        List<Tipocontratacion> tipos = tipoContratacionService.findAll();
        return ResponseEntity.ok(tipos);
    }

    // Obtener un tipo de contratación por ID
    @GetMapping("/{id}")
    public ResponseEntity<Tipocontratacion> getTipoContratacionById(@PathVariable Integer id) {
        Optional<Tipocontratacion> tipoOptional = tipoContratacionService.findById(id);

        if (tipoOptional.isPresent()) {
            return ResponseEntity.ok(tipoOptional.get());
        } else {
            return ResponseEntity.notFound().build(); // Devuelve un 404 si no se encuentra el tipo de contratación
        }
    }

    // Crear un nuevo tipo de contratación
    @PostMapping
    public ResponseEntity<Tipocontratacion> createTipoContratacion(@Valid @RequestBody Tipocontratacion tipoContratacion) {
        Tipocontratacion newTipo = tipoContratacionService.save(tipoContratacion);
        return ResponseEntity.status(201).body(newTipo);
    }

    // Actualizar un tipo de contratación existente
    @PutMapping("/{id}")
    public ResponseEntity<Tipocontratacion> updateTipoContratacion(@PathVariable Integer id, @Valid @RequestBody Tipocontratacion tipoContratacion) {
        tipoContratacion.setId(id);
        Tipocontratacion updatedTipo = tipoContratacionService.save(tipoContratacion);
        return ResponseEntity.ok(updatedTipo);
    }

    // Eliminar un tipo de contratación
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTipoContratacion(@PathVariable Integer id) {
        tipoContratacionService.delete(id);
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