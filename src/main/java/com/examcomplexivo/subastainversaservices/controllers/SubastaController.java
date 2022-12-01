package com.examcomplexivo.subastainversaservices.controllers;

import com.examcomplexivo.subastainversaservices.models.Subasta;
import com.examcomplexivo.subastainversaservices.services.subasta.SubastaService;
import com.examcomplexivo.subastainversaservices.utils.FileUploadUtil;
import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT,
        RequestMethod.DELETE })
@Controller
@RequestMapping("/auth/subasta")
public class SubastaController {

    @Autowired
    private SubastaService subastaService;

    @GetMapping("listar")
    public List<Subasta> listar() {
        return subastaService.listar();
    }

    @GetMapping("listar/NoPuja/{idProveedor}")
    public List<Subasta> listarNoPuja(@PathVariable String idProveedor) {
        System.out.println("ID "+idProveedor);
        return subastaService.findBySubastaNoPujada(idProveedor);
    }

    @GetMapping("listar/{fechaInicio}/{fechaFin}")
    public List<Subasta> listarByFechas(@PathVariable(name = "fechaInicio", required = true) java.sql.Date fechaInicio,
                                @PathVariable(name = "fechaFin", required = true) java.sql.Date fechaFin) {
        return subastaService.findByFechas(fechaInicio, fechaFin);
    }

    @GetMapping("listar/{filtro}")
    public List<Subasta> listarByFiltros(@PathVariable(name = "filtro", required = true) String filtro) {
        return subastaService.findByFiltro(filtro);
    }

    @PostMapping("crear")
    @PreAuthorize("hasAnyRole('ADMIN','CLIENTE')")
    public ResponseEntity<?> crear(@Valid @RequestParam("subasta") String subasta, @RequestParam(value = "fichero" , required = false) MultipartFile multipartFile) {

        Gson gson = new Gson();
        Subasta nuevaSubasta = gson.fromJson(subasta, Subasta.class);

        //Guardamos la imagen
        try {
            if (multipartFile != null){
                String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
                //Establecemos el directorio donde se subiran nuestros ficheros
                String uploadDir = "photos";
                nuevaSubasta.setImgSubasta(fileName);
                FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            }
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("mensaje", "ERROR IMG" + e.getMessage()));
        }

        //TODO AGREGAR VALIDACION DE QUE EXISTA EL CLIENTE
        //TODO AGREGAR VALIDACION DE QUE NO VENGA SETEADO LOS VALORES DE OFERTAS
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(subastaService.guardar(nuevaSubasta));
    }

    @PutMapping("/editar/{idSubasta}")
    @PreAuthorize("hasAnyRole('ADMIN','CLIENTE')")
    public ResponseEntity<?> editarSubasta(@PathVariable(name = "idSubasta", required = true)Long idSubasta,
                                                   @RequestBody Subasta subasta){
        try{
            if (subastaService.findById(idSubasta).isPresent()) {
                subasta.setIdSubasta(idSubasta);
                subastaService.guardar(subasta);
                return ResponseEntity.ok().body(Collections.singletonMap("mensaje", "Subasta modificada correctamente."));
            }else{
                return ResponseEntity.badRequest().body(Collections.singletonMap("mensaje", "La subasta no existe."));
            }
        }catch(Exception ex){
            Logger.getLogger(SubastaController.class.getName()).log(Level.SEVERE,"NO SE PUDO EDITAR");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/eliminar/{idSubasta}")
    @PreAuthorize("hasAnyRole('ADMIN','CLIENTE')")
    public ResponseEntity<?> eliminarSubasta (@PathVariable(name = "idSubasta", required = true)String idSubasta){
        try{
            if (subastaService.findById(Long.parseLong(idSubasta)).isPresent()) {
                subastaService.eliminar(Long.parseLong(idSubasta));
                return ResponseEntity.ok().body(Collections.singletonMap("mensaje", "Subasta eliminada correctamente."));
            }else{
                return ResponseEntity.badRequest().body(Collections.singletonMap("mensaje", "La subasta no existe."));
            }
        }catch(Exception ex){
            Logger.getLogger(SubastaController.class.getName()).log(Level.SEVERE, "NO SE PUDO ELIMINAR");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("mensaje", "No se pudo eliminar."));
        }
    }

    @GetMapping("/photos/{filename}")
    public ResponseEntity<byte[]> getImage(@PathVariable("filename") String filename) {
        byte[] image = new byte[0];
        try {
            image = FileUtils.readFileToByteArray(new File("photos/" + filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
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
