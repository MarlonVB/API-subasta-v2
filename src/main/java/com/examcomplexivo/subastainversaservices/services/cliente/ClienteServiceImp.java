package com.examcomplexivo.subastainversaservices.services.cliente;

import com.examcomplexivo.subastainversaservices.models.Cliente;
import com.examcomplexivo.subastainversaservices.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImp implements ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Override
    public List<Cliente> listar() {
        return (List<Cliente>) repository.findAll();
    }

    @Override
    public Cliente guardar(Cliente cliente) {
        return repository.save(cliente);
    }

    @Override
    public List<Cliente> findByFiltros(String filtro) {
        return repository.findByFiltros(filtro);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cliente> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Cliente> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public Optional<Cliente> findByTelefono(String telefono) {
        return repository.findByTelefono(telefono);
    }

    @Override
    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
