package com.examcomplexivo.subastainversaservices.repositories;

import com.examcomplexivo.subastainversaservices.models.Subasta;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface SubastaRepository extends CrudRepository<Subasta, Long> {
    Optional<Subasta> findById(Long id);
    @Query(value = "SELECT su FROM Subasta su where su.fechaInicio= :fechaIncio AND su.fechaFin= :fechaFin")
    List<Subasta> findByFechas(Date fechaIncio, Date fechaFin);
    @Query(value = "SELECT * " +
            "FROM db_subasta.subasta AS su " +
            "JOIN db_subasta.cliente AS c ON su.id_cliente = c.id_cliente " +
            "JOIN db_subasta.personas AS per ON c.id_cliente = per.id_persona " +
            "JOIN db_subasta.servicio AS ser ON su.id_servicio = ser.id_servicio " +
            "WHERE per.nombre LIKE %:filtro% " +
            "OR per.apellido LIKE %:filtro% " +
            "OR per.email LIKE %:filtro% " +
            "OR ser.nombre_servicio LIKE %:filtro% " +
            "OR su.estado_subasta LIKE %:filtro% " +
            "OR su.titulo_subasta LIKE %:filtro% " +
            "OR su.hora_cierre_subasta LIKE %:filtro%",
            nativeQuery = true)
    List<Subasta> findByFiltro(String filtro);
}
