package com.examcomplexivo.subastainversaservices.models;

//import io.swagger.annotations.ApiModelProperty;

import lombok.Data;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@XmlRootElement
@Table(name = "oferta")
public class Oferta implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_oferta")
    private Long idOferta;
    @Column(name = "percio_oferta")
    private Double percioOferta;

    @Column(name = "fecha_oferta")
    private Date fecha;
    @Column(name = "comentario_calificacion_oferta")
    private String comentario_calificacion_oferta;
    @Column(name = "estado_oferta")
    private boolean estado;
    @Column(name = "calificacion_oferta")
    private double calificacion;

    @ManyToOne()
    @JoinColumn(referencedColumnName="id_proveedor", nullable = false)
    private Proveedor proveedor;


    @ManyToOne()
    @JoinColumn(referencedColumnName="id_subasta", nullable = false)
    private Subasta subasta;
}
