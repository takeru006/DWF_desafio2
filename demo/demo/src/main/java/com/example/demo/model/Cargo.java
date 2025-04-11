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
@Table(name = "cargos", schema = "examen")
public class Cargo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCargo", nullable = false)
    private Integer id;

    @NotBlank(message = "El campo 'cargo' no puede estar vacío")
    @Size(max = 50, message = "El campo 'cargo' no puede exceder los 50 caracteres")
    @Column(name = "cargo", nullable = false, length = 50)
    private String cargo;

    @Lob
    @Column(name = "descripcionCargo")
    private String descripcionCargo;

    @Column(name = "jefatura")
    private Boolean jefatura;

    // Relación OneToMany con Contratacione
    @OneToMany(mappedBy = "cargo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Contratacione> contrataciones; // Cambiado a 'contrataciones' para mayor claridad
}