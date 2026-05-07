package ar.edu.utn.dds.k3003.model;

public class NecesidadExtraordinaria extends NecesidadMaterial {
    public NecesidadExtraordinaria(
            String entidadID,
            Integer nivelDeUrgencia,
            String descripcion,
            String productoSolicitadoID,
            Integer cantidadNecesaria)
    {
        super(entidadID,nivelDeUrgencia,descripcion,productoSolicitadoID, cantidadNecesaria);
    }

    @Override
    public void satisfacer(Integer cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("Cantidad debe ser positiva");
        }
        this.cantidadRecibida += cantidad;
    }


}
