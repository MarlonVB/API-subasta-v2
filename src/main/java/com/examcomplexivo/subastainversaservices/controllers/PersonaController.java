package com.examcomplexivo.subastainversaservices.controllers;

import com.examcomplexivo.subastainversaservices.models.Persona;
import com.examcomplexivo.subastainversaservices.services.persona.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT,
        RequestMethod.DELETE })
@RequestMapping("/persona")
public class PersonaController {

    @Autowired
    private PersonaService service;

    @GetMapping("/listar")
    public List<Persona> listar() {
        return service.listar();
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Persona persona, BindingResult result) {
        if (result.hasErrors()) {
            return validar(result);
        }
        if (service.findByNombre(persona.getNombre()).isPresent()) {
            return ResponseEntity.badRequest().body(
                    Collections.singletonMap("mensaje", "ya existe el email")
            );
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.guardar(persona));
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
