package com.examcomplexivo.subastainversaservices.security.dto;

import com.examcomplexivo.subastainversaservices.security.entity.Rol;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

public class NuevoUsuario {
    @NotBlank
    private String nombreUsuario;
    @NotBlank
    private String contraseniaUsuario;
    private Set<String> roles = new HashSet<>();

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContraseniaUsuario() {
        return contraseniaUsuario;
    }

    public void setContraseniaUsuario(String contraseniaUsuario) {
        this.contraseniaUsuario = contraseniaUsuario;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public NuevoUsuario(String nombreUsuario, String contraseniaUsuario, Set<String> roles) {
        this.nombreUsuario = nombreUsuario;
        this.contraseniaUsuario = contraseniaUsuario;
        this.roles = roles;
    }
}
