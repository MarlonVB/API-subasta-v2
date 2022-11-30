package com.examcomplexivo.subastainversaservices.security.service.rol;

import com.examcomplexivo.subastainversaservices.security.entity.Rol;
import com.examcomplexivo.subastainversaservices.security.entity.Usuario;
import com.examcomplexivo.subastainversaservices.security.enums.RolNombre;

import java.util.Optional;

public interface RolService {
    Optional<Rol> findByRolNombre(RolNombre rolNombre);
    Rol guardar(Rol rol);
}
