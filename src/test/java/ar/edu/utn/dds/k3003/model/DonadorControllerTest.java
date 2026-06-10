package ar.edu.utn.dds.k3003.controllers;

import ar.edu.utn.dds.k3003.Fachada;
import ar.edu.utn.dds.k3003.catedra.dtos.donadoresYEntidades.*;
import ar.edu.utn.dds.k3003.catedra.dtos.incentivos.CategoriaDonadorEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DonadorController.class)
class DonadorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Fachada fachada;

    @Autowired
    private ObjectMapper objectMapper;


    private String donadorJson() {
        return """
    {
      "id": null,
      "nombre": "Pepe",
      "apellido": "Pérez",
      "edad": 34,
      "email": "pepe.perez@example.com",
      "nroDocumento": "12345678",
      "domicilio": "Av. Corrientes 1234, CABA",
      "estado": "VERIFICADO",
      "categoria": "A"
    }
    """;
    }
    // tests acá
    @Test
    void crearDonador_ok() throws Exception {
        DonadorDTO donador = new DonadorDTO(
                null,
                "Pepe",
                "Pérez",
                34,
                "pepe.perez@example.com",
                "12345678",
                "Av. Corrientes 1234, CABA",
                EstadoDonadorEnum.VERIFICADO,
                "A"
        );

        when(fachada.agregarDonador(any(DonadorDTO.class)))
                .thenReturn(donador);

        mockMvc.perform(post("/donadores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(donadorJson()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Pepe"))
                .andExpect(jsonPath("$.estado").value("VERIFICADO"));
    }

    @Test
    void crearDonador_badRequest() throws Exception {
        when(fachada.agregarDonador(any(DonadorDTO.class)))
                .thenThrow(new IllegalArgumentException("datos invalidos"));

        mockMvc.perform(post("/donadores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(donadorJson()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void obtenerDonadorPorId_ok() throws Exception {
        DonadorDTO donador = new DonadorDTO(
                null,
                "Pepe",
                "Pérez",
                34,
                "pepe.perez@example.com",
                "12345678",
                "Av. Corrientes 1234, CABA",
                EstadoDonadorEnum.VERIFICADO,
                "A"
        );

        when(fachada.buscarDonadorPorID("1"))
                .thenReturn(donador);

        mockMvc.perform(get("/donadores/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.apellido").value("Pérez"));
    }
}
