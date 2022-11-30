package com.examcomplexivo.subastainversaservices.services.proveedor;

import com.examcomplexivo.subastainversaservices.models.Proveedor;
import com.examcomplexivo.subastainversaservices.repositories.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
public class ProveedorServiceImp implements ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Proveedor> listar() {
        return (List<Proveedor>) proveedorRepository.findAll();
    }

    @Override
    public Proveedor crear(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    @Override
    public Optional<Proveedor> findByEmail(String email) {
        return proveedorRepository.findByEmail(email);
    }

    @Override
    public Optional<Proveedor> findByTelefono(String telefono) {
        return proveedorRepository.findByTelefono(telefono);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Proveedor> findById(Long id) {
        return proveedorRepository.findById(id);
    }

    @Override
    public List<Proveedor> findByFiltros(String filtro) {
        return proveedorRepository.findByFiltros(filtro);
    }

    @Override
    public List<Proveedor> findByServicio(String servicio) {
        return proveedorRepository.findByServicio(servicio);
    }

    @Override
    public void eliminar(Long id) {
        proveedorRepository.deleteById(id);
    }
}
