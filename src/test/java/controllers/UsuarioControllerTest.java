package controllers;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.websocket.divertoland.api.config.ComponentConfig;
import com.websocket.divertoland.api.controllers.v1.UsuarioController;
import com.websocket.divertoland.domain.Usuario;
import com.websocket.divertoland.domain.dto.EntrarFilaRequestDTO;
import com.websocket.divertoland.domain.dto.LoginDTO;
import com.websocket.divertoland.domain.dto.UsuarioDTO;
import com.websocket.divertoland.services.abstractions.UsuarioService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private ComponentConfig componentConfig;

    @InjectMocks
    private UsuarioController usuarioController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUsuarioByLogin_shouldReturnUser() {
        var loginDTO = new LoginDTO(); // adicionar getter e setter
        var usuario = new Usuario();
        usuario.setEmail("email@test.com");

        when(usuarioService.getUsuarioByLogin(loginDTO)).thenReturn(usuario);

        ResponseEntity<?> response = usuarioController.getUsuarioByLogin(loginDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(usuario, response.getBody());
    }

    @Test
    void entrarFila_shouldCallService() {
        var requestDTO = new EntrarFilaRequestDTO();
        doNothing().when(usuarioService).entrarNaFila(requestDTO);

        ResponseEntity<?> response = usuarioController.entrarFila(requestDTO);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void sairFila_shouldCallService() {
        Long atracaoId = 1L;
        doNothing().when(usuarioService).sairDaFila(atracaoId);

        ResponseEntity<?> response = usuarioController.sairFila(atracaoId);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void obterPosicaoFila_shouldReturnPosition() {
        var requestDTO = new EntrarFilaRequestDTO();
        when(usuarioService.posicaoDaFila(requestDTO)).thenReturn(5);

        ResponseEntity<?> response = usuarioController.obterPosicaoFila(requestDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(5, response.getBody());
    }

    @Test
    void findById_shouldReturnUser() {
        Long userId = 1L;
        Usuario usuarioDTO = mock(Usuario.class);
        UsuarioDTO

        when(usuarioService.findById(userId)).thenReturn(usuarioDTO);

        ResponseEntity<?> response = usuarioController.findById(userId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(usuarioDTO, response.getBody());
    }
}
