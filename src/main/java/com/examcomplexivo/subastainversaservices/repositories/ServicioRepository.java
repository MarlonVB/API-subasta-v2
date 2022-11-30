package com.examcomplexivo.subastainversaservices.repositories;

import com.examcomplexivo.subastainversaservices.models.Servicio;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ServicioRepository extends CrudRepository<Servicio, Long> {
    Optional<Servicio> findByNombreServicio(String nombre_servicio);
    Optional<Servicio> findById(Long id);

    @Query(value = "SELECT ser FROM Servicio ser WHERE ser.nombreServicio LIKE %:filtro%")
    List<Servicio> findByFiltro(String filtro);
}
