package ar.edu.utn.dds.k3003.model;

import java.time.LocalDate;

public class Queja {
    private String id;
    private String donacionId;
    private String donadorId;
    private String motivo;
    private LocalDate fecha;

    public Queja(
            String id,
            String donacionId,
            String donadorId,
            String motivo,
            LocalDate fecha)
    {
        this.id = id;
        this.donacionId = donacionId;
        this.donadorId = donadorId;
        this.motivo = motivo;
        this.fecha = fecha;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDonacionId() {
        return donacionId;
    }

    public void setDonacionId(String donacionId) {
        this.donacionId = donacionId;
    }

    public String getDonadorId() {
        return donadorId;
    }

    public void setDonadorId(String donadorId) {
        this.donadorId = donadorId;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}