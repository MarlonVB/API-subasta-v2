package com.examcomplexivo.subastainversaservices.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "personas")
@Data
@AllArgsConstructor
public class Persona implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_persona;

    @NotBlank
    private String nombre;

    @NotBlank
    private String apellido;

    @Column(unique = true)
    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Column(unique = true)
    private String telefono;

    @NotBlank
    private String direccion;

    public Persona() {
    }
}
