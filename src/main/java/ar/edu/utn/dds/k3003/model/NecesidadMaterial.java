package ar.edu.utn.dds.k3003.model;

public abstract class NecesidadMaterial {
    private String id;
    private String entidadID;
    private Integer nivelDeUrgencia;
    private String descripcion;
    private String productoSolicitadoID;
    private   Integer cantidadObjetivo;
    protected Integer cantidadRecibida = 0;


    public NecesidadMaterial(String descripcion, Integer cantidadObjetivo) {
        this.descripcion = descripcion;
        this.cantidadObjetivo = cantidadObjetivo;
    }

    public NecesidadMaterial(String entidadID, Integer nivelDeUrgencia, String descripcion, String productoSolicitadoID, Integer cantidadObjetivo) {
        this.entidadID = entidadID;
        this.nivelDeUrgencia = nivelDeUrgencia;
        this.descripcion = descripcion;
        this.productoSolicitadoID = productoSolicitadoID;
        this.cantidadObjetivo = cantidadObjetivo;
    }

    public abstract void satisfacer(Integer cantidad);

    public boolean estaSatisfecha() {
        return cantidadRecibida >= cantidadObjetivo;
    }

    public boolean esDelProducto(String producto) {
        return this.productoSolicitadoID.equalsIgnoreCase(producto);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEntidadID() {
        return entidadID;
    }

    public void setEntidadID(String entidadID) {
        this.entidadID = entidadID;
    }

    public Integer getNivelDeUrgencia() {
        return nivelDeUrgencia;
    }

    public void setNivelDeUrgencia(Integer nivelDeUrgencia) {
        this.nivelDeUrgencia = nivelDeUrgencia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getProductoSolicitadoID() {
        return productoSolicitadoID;
    }

    public void setProductoSolicitadoID(String productoSolicitadoID) {
        this.productoSolicitadoID = productoSolicitadoID;
    }

    public Integer getCantidadObjetivo() {
        return cantidadObjetivo;
    }

    public void setCantidadObjetivo(Integer cantidadObjetivo) {
        this.cantidadObjetivo = cantidadObjetivo;
    }

    public Integer getCantidadRecibida() {
        return cantidadRecibida;
    }

    public void setCantidadRecibida(Integer cantidadRecibida) {
        this.cantidadRecibida = cantidadRecibida;
    }
}
