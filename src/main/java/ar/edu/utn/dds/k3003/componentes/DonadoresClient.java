package ar.edu.utn.dds.k3003.componentes;

import ar.edu.utn.dds.k3003.catedra.dtos.donaciones.ProductoDTO;
import ar.edu.utn.dds.k3003.catedra.dtos.incentivos.InsigniaDTO;
import ar.edu.utn.dds.k3003.catedra.dtos.incentivos.MisionDTO;
import ar.edu.utn.dds.k3003.exceptions.ProductoNoEncontradoException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class DonadoresClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl;

    public DonadoresClient(@Value("${url.donadoresYEntidades}") String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public ProductoDTO getProductoPorId(String productoId)
    {
        try {
            String url = baseUrl + "/productos/" + productoId;
            return restTemplate.getForObject(url, ProductoDTO.class);

        }
        catch (HttpClientErrorException.NotFound e) {
            throw new ProductoNoEncontradoException("El producto no existe");
        }
        catch (Exception e) {
            throw new RuntimeException("Error al consultar el producto en Donadores", e);
        }
    }
}