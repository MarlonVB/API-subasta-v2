package com.examcomplexivo.subastainversaservices.security.repository;

import com.examcomplexivo.subastainversaservices.security.entity.Usuario;
import com.examcomplexivo.subastainversaservices.security.enums.RolNombre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRespository extends CrudRepository<Usuario, Long> {
    // * El nombre del memtodo debe ser igual al de los atributos del entity sino bota error
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
    boolean existsByNombreUsuario(String nombreUsuario);

}
