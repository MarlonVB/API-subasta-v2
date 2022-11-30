package com.examcomplexivo.subastainversaservices.services.subasta;


import com.examcomplexivo.subastainversaservices.models.Subasta;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface SubastaService {
    List<Subasta> listar();

    List<Subasta> findByFechas(Date fechaIncio, Date fechaFin);

    List<Subasta> findByFiltro(String filtro);

    Optional<Subasta> findById(Long id);
    Subasta guardar(Subasta subasta);

    void eliminar(Long id);

}
