package com.examcomplexivo.subastainversaservices.controllers;

import com.examcomplexivo.subastainversaservices.models.Oferta;
import com.examcomplexivo.subastainversaservices.services.oferta.OfertaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT,
        RequestMethod.DELETE })
@RequestMapping("/auth/oferta")
public class OfertaController {

    @Autowired
    private OfertaService ofertaService;

    @GetMapping("listar")
    public List<Oferta> listar(){
        return ofertaService.listar();
    }

    @GetMapping("listar/subasta/{idSubasta}")
    public List<Oferta> listarBySubasta(@PathVariable(name = "idSubasta", required = true)Long idSubasta){
        System.out.println("ID "+idSubasta);
        return ofertaService.findBySubasta(idSubasta);
    }

    @PostMapping("crear")
    @PreAuthorize("hasAnyRole('ADMIN','PROVEEDOR')")
    public ResponseEntity<?> crear(@Valid @RequestBody Oferta oferta, BindingResult result){

//        Optional<Oferta> ofertaBD = ofertaService.findById(oferta.getIdOferta());
//        if(result.hasErrors()){
//            return validar(result);
//        }
//        if (ofertaBD.isPresent()){
//            return ResponseEntity.badRequest().body(
//                    Collections.singletonMap("Mensaje","Esta oferta ya fue registrada")
//            );
//        }
        return  ResponseEntity.status(HttpStatus.CREATED).body(ofertaService.guardar(oferta));
    }

    @PutMapping("/editar/{idOferta}")
    //@PreAuthorize("hasAnyRole('ADMIN','PROVEEDOR')")
    public ResponseEntity<?> editarOderta(@PathVariable(name = "idOferta", required = true)Long idOferta,
                                          @RequestBody Oferta oferta){
        System.out.println(idOferta);
        try{
            if (ofertaService.findById(idOferta).isPresent()){
                oferta.setIdOferta(idOferta);
                ofertaService.guardar(oferta);
                return ResponseEntity.ok().body(Collections.singletonMap("mensaje", "Oferta modificada correctamente."));
            }else {
                return ResponseEntity.badRequest().body(Collections.singletonMap("mensaje", "La oferta no existe."));
            }
        }catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/eliminar/{idOferta}")
    @PreAuthorize("hasAnyRole('ADMIN','PROVEEDOR')")
    public ResponseEntity<?> eliminarOferta (@PathVariable(name = "idOferta", required = true) Long idOferta){
        try{
            if (ofertaService.findById(idOferta).isPresent()){
                ofertaService.eliminar(idOferta);
                return ResponseEntity.ok().body(Collections.singletonMap("mensaje", "Oferta eliminada correctamente."));
            }else {
                return ResponseEntity.badRequest().body(Collections.singletonMap("mensaje", "La oferta no existe."));
            }
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("mensaje", "No se pudo eliminar."));
        }
    }

    @GetMapping("listar/id/{id}")
    public ResponseEntity<?> listarId(@RequestParam Long id){
        Optional<Oferta> ofertaOptional=ofertaService.findById(id);
        if (ofertaOptional.isPresent()) {

            return ResponseEntity.ok(ofertaOptional.get());
        }
        return ResponseEntity.badRequest().body(
                Collections.singletonMap("mensaje","No se ha encontrado ninguna oferta")
        );
    }

    @GetMapping("listar/fecha/{fecha}") //Buscar por fecha
    public ResponseEntity<?> listarFecha(@RequestParam String fecha){
        Optional<Oferta> ofertaOptional=ofertaService.findByFecha(parseFecha(fecha));
        if (ofertaOptional.isPresent()) {

            return ResponseEntity.ok(ofertaOptional.get());
        }
        return ResponseEntity.badRequest().body(
                Collections.singletonMap("mensaje","No se ha encontrado ninguna oferta")
        );
    }

    @GetMapping("listar/estado/{estado}")
    public ResponseEntity<?> listarEstado(@PathVariable Boolean estado){
        Optional<Oferta> ofertaOptional=ofertaService.findByEstado(estado);
        if (ofertaOptional.isPresent()) {

            return ResponseEntity.ok(ofertaOptional.get());
        }
        return ResponseEntity.badRequest().body(
                Collections.singletonMap("mensaje","No se ha encontrado ninguna oferta")
        );
    }

    @GetMapping("listar/proveedor/{proveedor}")
    public ResponseEntity<?> listarProveedor(@PathVariable Long proveedor){
        System.out.println(proveedor);
        Optional<Oferta> ofertaOptional=ofertaService.findbyProveedor(proveedor);
        if (ofertaOptional.isPresent()) {

            return ResponseEntity.ok(ofertaOptional.get());
        }
        return ResponseEntity.badRequest().body(
                Collections.singletonMap("mensaje","No se ha encontrado ninguna oferta")
        );
    }

    @GetMapping("listar/calificacion/{califiacion:.*}")
    public ResponseEntity<?> listarCalificacion(@PathVariable Double califiacion){
        System.out.println(califiacion);
        Optional<Oferta> ofertaOptional=ofertaService.findbyCalificaion(califiacion);
        if (ofertaOptional.isPresent()) {

            return ResponseEntity.ok(ofertaOptional.get());
        }
        return ResponseEntity.badRequest().body(
                Collections.singletonMap("mensaje","No se ha encontrado ninguna oferta")
        );
    }

    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "El campo" + err.getField()
                    + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }

    public Date parseFecha(String fecha)
    {
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
        Date fechaDate = null;
        try {
            fechaDate = formato.parse(fecha);
        }
        catch (ParseException ex)
        {
            System.out.println(ex);
        }
        return fechaDate;
    }

}
