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
@Table(name = "departamento", schema = "examen")
public class Departamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDepartamento", nullable = false)
    private Integer id;

    @NotBlank(message = "El nombre del departamento no puede estar vacío")
    @Size(max = 50, message = "El nombre del departamento no puede exceder los 50 caracteres")
    @Column(name = "nombreDepartamento", nullable = false, length = 50)
    private String nombreDepartamento;

    @Lob
    @Column(name = "descripcionDepartamento")
    private String descripcionDepartamento;

    // Relación OneToMany con Contratacion
    @OneToMany(mappedBy = "departamento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Contratacione> contrataciones;
}