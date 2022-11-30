package com.examcomplexivo.subastainversaservices.models;

//import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@XmlRootElement
@Entity
@Table(name = "servicio")
@AllArgsConstructor
public class Servicio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servicio")
    private long idServicio;
    @Column(name = "nombre_servicio", nullable = false, length = 50)
    private String nombreServicio;
    @Column(name = "descripcion_servicio", nullable = false, length = 200)
    private String descripcion_servicio;

    public Servicio() {
    }

    public Servicio(String nombreServicio, String descripcion_servicio) {
        this.nombreServicio = nombreServicio;
        this.descripcion_servicio = descripcion_servicio;
    }
}
