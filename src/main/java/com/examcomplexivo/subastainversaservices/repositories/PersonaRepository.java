package com.examcomplexivo.subastainversaservices.repositories;

import com.examcomplexivo.subastainversaservices.models.Persona;
import com.examcomplexivo.subastainversaservices.models.Proveedor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonaRepository extends CrudRepository<Persona, Long> {

    Optional<Persona> findByEmail(String email);

    Optional<Persona> findByTelefono(String telefono);

    Optional<Persona> findById(Long id);

    Optional<Persona> findByNombre(String nombre);
}
