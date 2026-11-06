package ar.edu.utn.dds.k3003.publisher;

import ar.edu.utn.dds.k3003.catedra.dtos.donadoresYEntidades.EntidadBeneficaDTO;
import ar.edu.utn.dds.k3003.events.entidadEvents.EntidadBeneficaActualizadaEvent;
import ar.edu.utn.dds.k3003.events.entidadEvents.EntidadBeneficaCreadaEvent;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class EntidadBeneficaPublisher {

    private final EventPublisher eventPublisher;

    public EntidadBeneficaPublisher(EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void notificarEntidadCreada(EntidadBeneficaDTO entidadDTO) {
        EntidadBeneficaCreadaEvent evento = new EntidadBeneficaCreadaEvent(
                entidadDTO.id(),
                entidadDTO.razonSocial(),
                entidadDTO.domicilio(),
                entidadDTO.telefono(),
                entidadDTO.correo(),
                Instant.now()
        );

        eventPublisher.publicar("entidad-creada", evento);
        System.out.println("Entidad benéfica creada enviada a RabbitMQ para el ID: " + entidadDTO.id());
    }

    public void notificarEntidadActualizada(Integer id, EntidadBeneficaDTO entidadDTO) {
        EntidadBeneficaActualizadaEvent evento = new EntidadBeneficaActualizadaEvent(
                id,
                entidadDTO.razonSocial(),
                entidadDTO.domicilio(),
                entidadDTO.telefono(),
                entidadDTO.correo(),
                Instant.now()
        );

        eventPublisher.publicar("entidad-actualizada", evento);
        System.out.println("Entidad benéfica actualizada enviada a RabbitMQ para el ID: " + id);
    }
}