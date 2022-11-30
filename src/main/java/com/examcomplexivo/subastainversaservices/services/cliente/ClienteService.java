package com.examcomplexivo.subastainversaservices.services.cliente;


import com.examcomplexivo.subastainversaservices.models.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteService {
    List<Cliente> listar();

    Cliente guardar(Cliente cliente);

    List<Cliente> findByFiltros(String filtro);

    Optional<Cliente> findById(Long id);

    Optional<Cliente> findByEmail(String email);

    Optional<Cliente> findByTelefono(String telefono);

    void eliminar(Long id);
}
