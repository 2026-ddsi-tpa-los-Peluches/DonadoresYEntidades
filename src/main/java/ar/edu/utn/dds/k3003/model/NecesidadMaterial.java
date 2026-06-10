package ar.edu.utn.dds.k3003.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "necesidades_materiales")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_necesidad", discriminatorType = DiscriminatorType.STRING)
public abstract class NecesidadMaterial {

        @Id
        private String id;

        // Relación ManyToOne con EntidadBenefica (la contraparte del OneToMany)
        @ManyToOne
        @JoinColumn(name = "entidad_id", referencedColumnName = "id")
        private EntidadBenefica entidadBenefica;

        // Si querés seguir guardando el String entidadID además de la relación,
        // tenés que poner insertable = false, updatable = false.
        // O mejor, borrá entidadID y manejate solo con el objeto entidadBenefica.
        @Column(name = "entidad_id_string", insertable = false, updatable = false)
        private String entidadID;

        @Column(name = "nivel_urgencia")
        private Integer nivelDeUrgencia;

        @Column(name = "descripcion")
        private String descripcion;

        @Column(name = "producto_solicitado_id")
        private String productoSolicitadoID;

        @Column(name = "cantidad_objetivo")
        private Integer cantidadObjetivo;

        @Column(name = "cantidad_recibida")
        public Integer cantidadRecibida = 0;

        // Constructor vacío
        public NecesidadMaterial() {
        }

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

}
