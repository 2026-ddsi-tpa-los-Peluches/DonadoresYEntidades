package ar.edu.utn.dds.k3003.model;

import ar.edu.utn.dds.k3003.catedra.dtos.donadoresYEntidades.EstadoDonadorEnum;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "entidades_beneficas")
public class EntidadBenefica {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @Column(name = "razon_social")
        private String razonSocial;

        @Column(name = "domicilio")
        private String domicilio;

        @Column(name = "telefono")
        private String telefono;

        @Column(name = "email")
        private String email;

        // Relación OneToMany: Una Entidad tiene muchas Necesidades
        // cascade = CascadeType.ALL hace que si guardás la entidad, se guarden sus necesidades solas.
        @OneToMany(mappedBy = "entidadBenefica", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        private List<NecesidadMaterial> necesidades = new ArrayList<>();

        // Constructor vacío obligatorio de JPA
        public EntidadBenefica() {
        }

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

}
