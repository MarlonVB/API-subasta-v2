package com.examcomplexivo.subastainversaservices.services.proveedor;

import com.examcomplexivo.subastainversaservices.models.Proveedor;

import java.util.List;
import java.util.Optional;

public interface ProveedorService {

    List<Proveedor> listar();

    Proveedor crear(Proveedor proveedor);

    Optional<Proveedor> findByEmail(String email);

    Optional<Proveedor> findByTelefono(String telefono);

    Optional<Proveedor> findById(Long id);

    List<Proveedor> findByFiltros(String filtro);

    List<Proveedor> findByServicio(String servicio);

    void eliminar (Long id);
}
