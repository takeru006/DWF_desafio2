package com.example.demo.controller;

import com.example.demo.model.Empleado;
import com.example.demo.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.http.HttpStatus;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    // Obtener todos los empleados
    @GetMapping
    public ResponseEntity<List<Empleado>> getAllEmpleados() {
        List<Empleado> empleados = empleadoService.findAll();
        return ResponseEntity.ok(empleados);
    }

    // Obtener un empleado por ID
    @GetMapping("/{id}")
    public ResponseEntity<Empleado> getEmpleadoById(@PathVariable Integer id) {
        Optional<Empleado> empleadoOptional = empleadoService.findById(id);

        if (empleadoOptional.isPresent()) {
            return ResponseEntity.ok(empleadoOptional.get());
        } else {
            return ResponseEntity.notFound().build(); // Devuelve un 404 si no se encuentra el empleado
        }
    }

    // Crear un nuevo empleado
    @PostMapping
    public ResponseEntity<Empleado> createEmpleado(@Valid @RequestBody Empleado empleado) {
        try {
            Empleado newEmpleado = empleadoService.save(empleado);
            return ResponseEntity.status(HttpStatus.CREATED).body(newEmpleado);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null); // 409 Conflict
        }
    }

    // Actualizar un empleado existente
    @PutMapping("/{id}")
    public ResponseEntity<Empleado> updateEmpleado(@PathVariable Integer id, @Valid @RequestBody Empleado empleado) {
        Optional<Empleado> empleadoOptional = empleadoService.findById(id);
        if (!empleadoOptional.isPresent()) {
            return ResponseEntity.notFound().build(); // Devuelve un 404 si no se encuentra el empleado
        }
        empleado.setId(id);
        Empleado updatedEmpleado = empleadoService.save(empleado);
        return ResponseEntity.ok(updatedEmpleado);
    }

    // Eliminar un empleado
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmpleado(@PathVariable Integer id) {
        empleadoService.delete(id);
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