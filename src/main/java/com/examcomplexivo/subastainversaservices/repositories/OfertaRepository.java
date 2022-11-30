package com.examcomplexivo.subastainversaservices.repositories;

import com.examcomplexivo.subastainversaservices.models.Oferta;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface OfertaRepository extends CrudRepository<Oferta, Long> {

    Optional<Oferta> findById(Long id);

    Optional<Oferta> findByFecha(Date fecha);

    Optional<Oferta> findByEstado(Boolean estado);

    @Query(value = "SELECT * FROM db_subasta.oferta AS su WHERE su.subasta_id_subasta = :id", nativeQuery = true)
    List<Oferta> findBySubasta(Long id);

    @Query(value = "SELECT o FROM Oferta o WHERE o.proveedor.id_persona = :filtro")
    Optional<Oferta> findbyProveedor(Long filtro);

    @Query(value = "SELECT o FROM Oferta o WHERE o.calificacion = :calificacion")
    Optional<Oferta> findbyCalificaion(Double calificacion);
}
