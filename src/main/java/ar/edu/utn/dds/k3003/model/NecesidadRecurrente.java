package ar.edu.utn.dds.k3003.model;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@DiscriminatorValue("RECURRENTE")
public class NecesidadRecurrente extends NecesidadMaterial {



    protected NecesidadRecurrente() {
    }



    public NecesidadRecurrente(
            Integer nivelDeUrgencia,
            String descripcion,
            String productoSolicitadoID,
            Integer cantidadNecesaria)
    {
        super(nivelDeUrgencia,descripcion,productoSolicitadoID, cantidadNecesaria);
    }

    @Override
    public void satisfacer(Integer cantidad) {
        if (cantidad < this.getCantidadObjetivo()) {
            throw new IllegalArgumentException("Cantidad debe ser mayor a la necesaria");
        }
        this.setCantidadRecibida(this.getCantidadRecibida() + cantidad);
    }

}
