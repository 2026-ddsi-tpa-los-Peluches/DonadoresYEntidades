package ar.edu.utn.dds.k3003.componentes;


import ar.edu.utn.dds.k3003.catedra.dtos.donaciones.DonacionDTO;
import ar.edu.utn.dds.k3003.catedra.dtos.donaciones.EstadoDonacionEnum;
import ar.edu.utn.dds.k3003.catedra.dtos.donaciones.ProductoDTO;
import ar.edu.utn.dds.k3003.catedra.dtos.incentivos.InsigniaDTO;
import ar.edu.utn.dds.k3003.catedra.dtos.incentivos.MisionDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

@Service
public class DonacionesClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl;

    public DonacionesClient(@Value("${DONACIONES_URL}") String baseUrl) {
        this.baseUrl = baseUrl;
    }

    // // PATCH /donaciones/{id}/estado"
    public Boolean existeProducto (String productoID) {
        try {
            String url = baseUrl + "/productos/" + productoID;

            restTemplate.getForObject(url, ProductoDTO.class);
            return true;

        } catch (HttpClientErrorException.NotFound e) {
            return false;
        } catch (Exception e) {
            throw new RuntimeException("Error de comunicación al buscar el producto: " + productoID, e);
        }
    }
}