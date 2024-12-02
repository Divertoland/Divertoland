package controllers;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.*;

import com.websocket.divertoland.api.controllers.v1.AtracaoController;
import com.websocket.divertoland.domain.Atracao;
import com.websocket.divertoland.services.abstractions.AtracaoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

class AtracaoControllerTest {

    @Mock
    private AtracaoService atracaoService;

    @InjectMocks
    private AtracaoController atracaoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listarAtracao_shouldReturnAtracoes_whenNotEmpty() {
        var atracao = new Atracao();
        atracao.setNome("Test Atracao");

        when(atracaoService.listAtracoesAsync()).thenReturn(List.of(atracao));

        ResponseEntity<?> response = atracaoController.listarAtracao();

        assertEquals(OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(((List<Atracao>) response.getBody()).contains(atracao));
    }

    @Test
    void listarAtracao_shouldReturnNoContent_whenEmpty() {
        when(atracaoService.listAtracoesAsync()).thenReturn(List.of());

        ResponseEntity<?> response = atracaoController.listarAtracao();

        assertEquals(NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }
}
