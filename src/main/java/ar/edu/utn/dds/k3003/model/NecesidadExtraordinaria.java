package ar.edu.utn.dds.k3003.model;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("EXTRAORDINARIA")
public class NecesidadExtraordinaria extends NecesidadMaterial {



    // ¡ESTO ES LO QUE TE FALTA!
    protected NecesidadExtraordinaria() {
    }

    public NecesidadExtraordinaria(
            Integer nivelDeUrgencia,
            String descripcion,
            String productoSolicitadoID,
            Integer cantidadNecesaria)
    {
        super(nivelDeUrgencia,descripcion,productoSolicitadoID, cantidadNecesaria);
    }

    @Override
    public void satisfacer(Integer cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("Cantidad debe ser positiva");
        }
        this.cantidadRecibida += cantidad;
    }


}
