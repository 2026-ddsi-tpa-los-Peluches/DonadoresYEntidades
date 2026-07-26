package ar.edu.utn.dds.k3003.componentes;

import ar.edu.utn.dds.k3003.catedra.dtos.donadoresYEntidades.NecesidadMaterialDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LogisticaClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl;

    public LogisticaClient(@Value("${LOGISTICA_SERVICE_URL}") String baseUrl) {

        System.out.println("LOGISTICA URL = " + baseUrl);

        this.baseUrl = baseUrl;
    }

    public Integer asignarProductoAEntidad(NecesidadMaterialDTO necesidad) {
        try {
            String url = baseUrl + "/asignaciones";

            ResponseEntity<Integer> response = restTemplate.postForEntity(url, necesidad, Integer.class);

            return response.getBody();

        } catch (Exception e) {
            throw new RuntimeException("Error de comunicación al gestionar la donación en Logística", e);
        }
    }
}