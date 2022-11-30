package com.examcomplexivo.subastainversaservices.services.oferta;

import com.examcomplexivo.subastainversaservices.models.Oferta;
import com.examcomplexivo.subastainversaservices.repositories.OfertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OfertaServiceImp implements OfertaService{

    @Autowired
    OfertaRepository ofertaRepository;

    @Override
    public List<Oferta> listar() {
        return (List<Oferta>) ofertaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Oferta> findById(Long id) {
        return ofertaRepository.findById(id);
    }

    @Override
    public Optional<Oferta> findByFecha(Date fecha) {
        return ofertaRepository.findByFecha(fecha);
    }

    @Override
    public Optional<Oferta> findByEstado(Boolean estado) {
        return ofertaRepository.findByEstado(estado);
    }

    @Override
    public Optional<Oferta> findbyProveedor(Long filtro) {
        return ofertaRepository.findbyProveedor(filtro);
    }

    @Override
    public List<Oferta> findBySubasta(Long id) {
        return ofertaRepository.findBySubasta(id);
    }

    @Override
    public Optional<Oferta> findbyCalificaion(Double filtro) {
        return ofertaRepository.findbyCalificaion(filtro);
    }

    @Override
    @Transactional
    public Oferta guardar(Oferta oferta) {
        return ofertaRepository.save(oferta);
    }

    @Override
    public void eliminar(Long id) {
        ofertaRepository.deleteById(id);
    }
}
