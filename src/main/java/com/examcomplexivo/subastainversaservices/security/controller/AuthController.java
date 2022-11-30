package com.examcomplexivo.subastainversaservices.security.controller;

import com.examcomplexivo.subastainversaservices.security.dto.JwtDto;
import com.examcomplexivo.subastainversaservices.security.dto.LoginUsuario;
import com.examcomplexivo.subastainversaservices.security.dto.NuevoUsuario;
import com.examcomplexivo.subastainversaservices.security.entity.Rol;
import com.examcomplexivo.subastainversaservices.security.entity.Usuario;
import com.examcomplexivo.subastainversaservices.security.enums.RolNombre;
import com.examcomplexivo.subastainversaservices.security.jwt.JwtProvider;
import com.examcomplexivo.subastainversaservices.security.service.UsersDetailsImp;
import com.examcomplexivo.subastainversaservices.security.service.rol.RolServiceImp;
import com.examcomplexivo.subastainversaservices.security.service.usuario.UsuarioServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT,
        RequestMethod.DELETE })
public class AuthController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioServiceImp usuarioServiceImp;

    @Autowired
    RolServiceImp rolServiceImp;

    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/crear/admin")
    public ResponseEntity<?> nuevo(@Valid @RequestBody NuevoUsuario nuevoUsuario, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return validar(bindingResult);
        }
        if (usuarioServiceImp.existsByNombreUsuario(nuevoUsuario.getNombreUsuario())){
            return ResponseEntity.badRequest().body(Collections.singletonMap("mensaje", "Este usuario ya existe"));
        }
        Usuario usuario = new Usuario(nuevoUsuario.getNombreUsuario(), passwordEncoder.encode(nuevoUsuario.getContraseniaUsuario()));
        Set<Rol> roles = new HashSet<>();
        roles.add(rolServiceImp.findByRolNombre(RolNombre.ROLE_ADMIN).get());

        usuario.setRoles(roles);
        usuarioServiceImp.guardar(usuario);
        return ResponseEntity.ok().body(Collections.singletonMap("mensaje", "Usuario registrado exitosamente."));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUsuario loginUsuario, BindingResult bindingResult){
        System.out.println("A1");
        if (bindingResult.hasErrors()) {
            return ResponseEntity.ok().body((JwtDto) Collections.singletonMap("mensaje", "Campos invalidos"));
        }
        System.out.println("B1");
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(loginUsuario.getNombreUsuario(), loginUsuario.getContraseniaUsuario()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        UserDetails usersDetails = (UserDetails) authentication.getPrincipal();
        JwtDto jwtDto = new JwtDto(jwt, usersDetails.getUsername(), usersDetails.getAuthorities());
        System.out.println("C");
        return new ResponseEntity(jwtDto, HttpStatus.OK);
    }

    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "El campo" + err.getField()
                    + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
}
