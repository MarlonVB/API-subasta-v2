package com.examcomplexivo.subastainversaservices.services.persona;


import com.examcomplexivo.subastainversaservices.models.Persona;

import java.util.List;
import java.util.Optional;

public interface PersonaService {
    List<Persona> listar();

    Optional<Persona> findById(Long id);

    Optional<Persona> findByNombre(String nombre);

    Optional<Persona> findByEmail(String email);

    Optional<Persona> findByTelefono(String telefono);

    Persona guardar(Persona persona);

    void eliminar(Long id);

}
