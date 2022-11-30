package com.examcomplexivo.subastainversaservices.security.repository;

import com.examcomplexivo.subastainversaservices.security.entity.Rol;
import com.examcomplexivo.subastainversaservices.security.enums.RolNombre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends CrudRepository<Rol, Long>{
    Optional<Rol> findByRolNombre(RolNombre rolNombre);
}
