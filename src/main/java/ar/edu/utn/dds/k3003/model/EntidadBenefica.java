package ar.edu.utn.dds.k3003.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EntidadBenefica {
    private String id;
    private String razonSocial;
    private String domicilio;
    private String telefono;
    private String email;
    private List<NecesidadMaterial> necesidades = new ArrayList<>();

    public EntidadBenefica(
            String razonSocial,
            String domicilio,
            String telefono,
            String email)
    {
        this.razonSocial = razonSocial;
        this.domicilio = domicilio;
        this.telefono = telefono;
        this.email = email;
    }


    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
