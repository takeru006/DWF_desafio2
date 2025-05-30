package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tipocontratacion", schema = "examen")
public class Tipocontratacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTipoContratacion", nullable = false)
    private Integer id;

    @NotBlank(message = "El tipo de contratación no puede estar vacío")
    @Size(max = 100, message = "El tipo de contratación no puede exceder los 100 caracteres")
    @Column(name = "tipoContratacion", nullable = false, length = 100)
    private String tipoContratacion;

    // Relación OneToMany con Contratacion
    @OneToMany(mappedBy = "tipoContratacion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Contratacione> contrataciones;
}