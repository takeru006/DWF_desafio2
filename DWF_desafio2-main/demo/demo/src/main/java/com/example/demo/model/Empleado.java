package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "empleados", schema = "examen")
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEmpleado", nullable = false)
    private Integer id;

    @NotBlank(message = "El número de DUI no puede estar vacío")
    @Size(min = 9, max = 9, message = "El número de DUI debe tener exactamente 9 caracteres")
    @Column(name = "numeroDui", nullable = false, length = 9)
    private String numeroDui;

    @NotBlank(message = "El nombre de la persona no puede estar vacío")
    @Size(max = 50, message = "El nombre de la persona no puede exceder los 50 caracteres")
    @Column(name = "nombrePersona", nullable = false, length = 50)
    private String nombrePersona;

    @NotBlank(message = "El usuario no puede estar vacío")
    @Size(max = 50, message = "El usuario no puede exceder los 50 caracteres")
    @Column(name = "usuario", nullable = false, length = 50)
    private String usuario;

    @Size(max = 9, message = "El número de teléfono no puede exceder los 9 caracteres")
    @Column(name = "numeroTelefono", length = 9)
    private String numeroTelefono;

    @Email(message = "El correo institucional debe ser válido")
    @Size(max = 50, message = "El correo institucional no puede exceder los 50 caracteres")
    @Column(name = "correoInstitucional", length = 50)
    private String correoInstitucional;

    @Column(name = "fechaNacimiento")
    private LocalDate fechaNacimiento;

    // Relación OneToMany con Contratacione
    @OneToMany(mappedBy = "empleado", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Contratacione> contrataciones; // Cambiado a 'empleado' en mappedBy
}