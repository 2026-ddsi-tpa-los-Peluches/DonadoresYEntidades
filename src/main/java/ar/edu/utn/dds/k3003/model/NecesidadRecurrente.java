package ar.edu.utn.dds.k3003.model;

public class NecesidadRecurrente extends NecesidadMaterial {
    public NecesidadRecurrente(
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
        if (cantidad < this.getCantidadObjetivo()) {
            throw new IllegalArgumentException("Cantidad debe ser mayor a la necesaria");
        }
        this.setCantidadRecibida(this.getCantidadRecibida() + cantidad);  //TODO
    }

}
