package ar.edu.utn.dds.k3003.model;

import ar.edu.utn.dds.k3003.catedra.dtos.donadoresYEntidades.EstadoDonadorEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class DonadorStats {

    private String id;
    private String nombre;
    private String apellido;
    private Integer edad;
    private EstadoDonadorEnum estado;
    private String categoria;
    private String misionActualID;
    private List<String> insigniasID = new ArrayList<>();


    public DonadorStats(
            String id,
            String nombre,
            String apellido,
            Integer edad,
            String misionActualID) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.estado = EstadoDonadorEnum.VERIFICADO;
        this.categoria = "Ocasional";
        this.misionActualID = misionActualID;
    }

    public DonadorStats(
            String id,
            String nombre,
            String apellido,
            Integer edad,
            EstadoDonadorEnum estado,
            String categoria,
            String misionActualID) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.estado = estado;
        this.categoria = categoria;
        this.misionActualID = misionActualID;
    }

}

