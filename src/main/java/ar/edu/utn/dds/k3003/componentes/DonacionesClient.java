package ar.edu.utn.dds.k3003.componentes;


import ar.edu.utn.dds.k3003.catedra.dtos.donaciones.DonacionDTO;
import ar.edu.utn.dds.k3003.catedra.dtos.donaciones.EstadoDonacionEnum;
import ar.edu.utn.dds.k3003.catedra.dtos.donaciones.ProductoDTO;
import ar.edu.utn.dds.k3003.catedra.dtos.incentivos.InsigniaDTO;
import ar.edu.utn.dds.k3003.catedra.dtos.incentivos.MisionDTO;
import ar.edu.utn.dds.k3003.exceptions.ProductoNoEncontradoException;
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

    public DonacionesClient(@Value("${url.donaciones}") String baseUrl) {
        this.baseUrl = baseUrl;
    }

    // // PATCH /donaciones/{id}/estado"
    public Boolean existeProducto(String productoID) {
        try {
            String url = baseUrl + "/productos/" + productoID;
            restTemplate.getForObject(url, ProductoDTO.class);
            return true; // 200 OK -> Existe el producto

        } catch (HttpClientErrorException.NotFound e) {
            String body = e.getResponseBodyAsString();

            // Si el body contiene nuestro mensaje o código de negocio, confirmamos que es un 404 de dominio
            if (body.contains("Producto no encontrado")) {
                return false; // El producto realmente no existe en la BD
            }

            // Si el body es genérico de Spring/Tomcat, fue un error de URL/Endpoint
            throw new RuntimeException("Error de configuración: La URL del endpoint de Donaciones no existe.", e);

        } catch (Exception e) {
            throw new RuntimeException("Error al comunicarse con el servicio de Donaciones: " + e.getMessage(), e);
        }
    }
}