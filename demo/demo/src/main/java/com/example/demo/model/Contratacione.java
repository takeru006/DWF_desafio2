package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "contrataciones", schema = "examen")
public class Contratacione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idContratacion", nullable = false)
    private Integer id;

    @NotNull(message = "El departamento no puede ser nulo")
    @ManyToOne
    @JoinColumn(name = "idDepartamento", nullable = false)
    private Departamento departamento;

    @NotNull(message = "El empleado no puede ser nulo")
    @ManyToOne
    @JoinColumn(name = "idEmpleado", nullable = false)
    private Empleado empleado;

    @NotNull(message = "El cargo no puede ser nulo")
    @ManyToOne
    @JoinColumn(name = "idCargo", nullable = false)
    private Cargo cargo;

    @NotNull(message = "El tipo de contratación no puede ser nulo")
    @ManyToOne
    @JoinColumn(name = "idTipoContratacion", nullable = false)
    private Tipocontratacion tipoContratacion;

    @FutureOrPresent(message = "La fecha de contratación debe ser hoy o en el futuro")
    @Column(name = "fechaContratacion")
    private LocalDate fechaContratacion;

    @NotNull(message = "El salario no puede ser nulo")
    @Positive(message = "El salario debe ser un número positivo")
    @Column(name = "salario", precision = 10, scale = 2)
    private BigDecimal salario;

    @Column(name = "estado")
    private Boolean estado;
}