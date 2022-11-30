package com.examcomplexivo.subastainversaservices.repositories;

import com.examcomplexivo.subastainversaservices.models.Proveedor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProveedorRepository extends CrudRepository<Proveedor, Long> {

    // * Busqueda personalizada con la etiqueta @Query con JPQL
    @Query(value = "SELECT p FROM Proveedor p " +
            "WHERE  " +
            "p.nombre LIKE %:filtro% " +
            "OR p.apellido LIKE %:filtro% " +
            "OR p.email LIKE %:filtro% " +
            "OR p.direccion LIKE %:filtro% " +
            "OR p.telefono LIKE %:filtro% " +
            "OR p.anios_experiencia LIKE %:filtro% "+
            "OR p.usuario.nombreUsuario LIKE %:filtro%")
    List<Proveedor> findByFiltros(String filtro);
    Optional<Proveedor> findByEmail(String email);

    Optional<Proveedor> findByTelefono(String telefono);

    // * Busqueda personalizada con la etiqueta @Query nativa (con SQL)
    @Query(value = "SELECT * " +
            "FROM db_subasta.proveedor AS p " +
            "JOIN db_subasta.personas AS per ON p.id_proveedor = per.id_persona " +
            "JOIN db_subasta.usuario AS u ON p.id_usuario = u.usuario_id " +
            "JOIN db_subasta.proveedor_servicios AS ps ON p.id_proveedor = ps.id_proveedor " +
            "JOIN db_subasta.servicio AS s ON ps.id_servicio = s.id_servicio " +
            "WHERE lower(s.nombre_servicio) LIKE %:servicio%",
            nativeQuery = true)
    List<Proveedor> findByServicio(String servicio);

    Optional<Proveedor> findById(Long id);

}
