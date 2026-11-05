package ar.edu.utn.dds.k3003.publisher;

import ar.edu.utn.dds.k3003.catedra.dtos.donaciones.CategoriaDTO;
import ar.edu.utn.dds.k3003.catedra.dtos.donaciones.ProductoDTO;
import ar.edu.utn.dds.k3003.catedra.dtos.donadoresYEntidades.NecesidadMaterialDTO;
import ar.edu.utn.dds.k3003.events.CantidadRecibidaActualizadaEvent;
import ar.edu.utn.dds.k3003.events.NecesidadActualizadaEvent;
import ar.edu.utn.dds.k3003.events.NecesidadCreadaEvent;
import ar.edu.utn.dds.k3003.events.NecesidadEliminadaEvent;
import ar.edu.utn.dds.k3003.model.NecesidadMaterial;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class NecesidadesPublisher {

    private final EventPublisher eventPublisher;

    // Inyección por constructor (siguiendo tu estándar)
    public NecesidadesPublisher(EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void notificarCantidadRecibida(Integer necesidadId, Integer cantidad) {
        CantidadRecibidaActualizadaEvent evento = new CantidadRecibidaActualizadaEvent(
                String.valueOf(necesidadId),
                cantidad
        );

        eventPublisher.publicar("necesidad-cantidad-recibida", evento);
        System.out.println("Evento de necesidad cantidad recibida enviado a RabbitMQ para la necesidad ID: " + necesidadId);
    }


    public void notificarNecesidadCreada(NecesidadMaterial necesidad, NecesidadMaterialDTO necesidadMaterialDTO, ProductoDTO productoDTO, CategoriaDTO categoriaDTO) {
        NecesidadCreadaEvent evento = new NecesidadCreadaEvent(
                String.valueOf(necesidad.getId()),
                necesidad.getEntidadBenefica() != null ? String.valueOf(necesidad.getEntidadBenefica().getId()) : null,
                necesidad.getEntidadBenefica() != null ? necesidad.getEntidadBenefica().getRazonSocial() : null,
                necesidad.getNivelDeUrgencia(),
                necesidad.getDescripcion(),
                necesidadMaterialDTO.tipo() != null ? necesidadMaterialDTO.tipo().toString() : null,
                necesidad.getProductoSolicitadoID(),
                productoDTO.nombre(),
                productoDTO.categoriaID(),
                categoriaDTO.subcategoriaID(),
                necesidad.getCantidadObjetivo()
        );
        eventPublisher.publicar("necesidad-creada", evento);
        System.out.println(" Necesidad creada enviada a RabbitMQ con ID: " + necesidad.getId());
    }

    public void notificarNecesidadActualizada(NecesidadMaterial necesidad) {
        NecesidadActualizadaEvent evento = new NecesidadActualizadaEvent(
                String.valueOf(necesidad.getId()),
                necesidad.getNivelDeUrgencia(),
                necesidad.getDescripcion()
        );
        eventPublisher.publicar("necesidad-actualizada", evento);
        System.out.println("Necesidad actualizada enviada a RabbitMQ con ID: " + necesidad.getId());
    }

    public void notificarNecesidadEliminada(Integer id) {
        NecesidadEliminadaEvent evento = new NecesidadEliminadaEvent(String.valueOf(id));
        eventPublisher.publicar("necesidad-eliminada", evento);
        System.out.println(" Necesidad eliminada enviada a RabbitMQ con ID: " + id);
    }
}
