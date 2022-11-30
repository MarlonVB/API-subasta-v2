package com.examcomplexivo.subastainversaservices.services.persona;

import com.examcomplexivo.subastainversaservices.models.Persona;
import com.examcomplexivo.subastainversaservices.repositories.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PersonaServiceImp implements PersonaService {

    @Autowired
    private PersonaRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Persona> listar() {
        return (List<Persona>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Persona> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Persona> findByNombre(String nombre) {
        return repository.findByNombre(nombre);
    }

    @Override
    public Optional<Persona> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public Optional<Persona> findByTelefono(String telefono) {
        return repository.findByTelefono(telefono);
    }

    @Override
    @Transactional
    public Persona guardar(Persona persona) {
        return repository.save(persona);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
