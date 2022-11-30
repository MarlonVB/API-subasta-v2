package com.examcomplexivo.subastainversaservices.repositories;

import com.examcomplexivo.subastainversaservices.models.Cliente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends CrudRepository<Cliente, Long> {

    // * Busqueda personalizada con la etiqueta @Query con JPQL
    @Query(value = "SELECT c FROM Cliente c " +
            "WHERE  " +
            "c.nombre LIKE %:filtro% " +
            "OR c.apellido LIKE %:filtro% " +
            "OR c.email LIKE %:filtro% " +
            "OR c.direccion LIKE %:filtro% " +
            "OR c.telefono LIKE %:filtro% " +
            "OR c.usuario.nombreUsuario LIKE %:filtro%")
    List<Cliente> findByFiltros(String filtro);
    Optional<Cliente> findById(Long id);

    Optional<Cliente> findByEmail(String email);

    Optional<Cliente> findByTelefono(String telefono);
}
