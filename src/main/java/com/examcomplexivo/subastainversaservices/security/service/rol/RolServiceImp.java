package com.examcomplexivo.subastainversaservices.security.service.rol;

import com.examcomplexivo.subastainversaservices.security.entity.Rol;
import com.examcomplexivo.subastainversaservices.security.enums.RolNombre;
import com.examcomplexivo.subastainversaservices.security.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class RolServiceImp implements RolService{

    @Autowired
    RolRepository rolRepository;

    @Override
    public Optional<Rol> findByRolNombre(RolNombre rolNombre) {
        return rolRepository.findByRolNombre(rolNombre);
    }

    @Override
    public Rol guardar(Rol rol) {
        return rolRepository.save(rol);
    }
}
