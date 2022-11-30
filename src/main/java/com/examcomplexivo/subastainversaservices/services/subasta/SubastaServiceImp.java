package com.examcomplexivo.subastainversaservices.services.subasta;

import com.examcomplexivo.subastainversaservices.models.Subasta;
import com.examcomplexivo.subastainversaservices.repositories.SubastaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SubastaServiceImp implements SubastaService {

    @Autowired
    private SubastaRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Subasta> listar() {
        return (List<Subasta>) repository.findAll();
    }

    @Override
    public List<Subasta> findByFechas(Date fechaIncio, Date fechaFin) {
        return repository.findByFechas(fechaIncio, fechaFin);
    }

    @Override
    public List<Subasta> findByFiltro(String filtro) {
        return repository.findByFiltro(filtro);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Subasta> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Subasta guardar(Subasta subasta) {
        return repository.save(subasta);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
