package services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.websocket.divertoland.api.config.ComponentConfig;
import com.websocket.divertoland.api.config.security.CustomPasswordEncoder;
import com.websocket.divertoland.domain.Atracao;
import com.websocket.divertoland.domain.Usuario;
import com.websocket.divertoland.domain.dto.EntrarFilaRequestDTO;
import com.websocket.divertoland.domain.dto.LoginDTO;
import com.websocket.divertoland.domain.dto.UsuarioDTO;
import com.websocket.divertoland.infrastructure.abstractions.repositories.AtracaoRepository;
import com.websocket.divertoland.infrastructure.abstractions.repositories.UsuarioRepository;
import com.websocket.divertoland.services.v1.UsuarioServiceV1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

class UsuarioServiceV1Test {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private AtracaoRepository atracaoRepository;

    @Mock
    private ComponentConfig componentConfig;

    @Mock
    private CustomPasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioServiceV1 usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUsuarioByLogin_shouldReturnUsuario_whenCredentialsAreValid() {
        var loginDTO = new LoginDTO("email@test.com", "password");
        var encodedPassword = "encodedPassword";
        var usuario = new Usuario();
        usuario.setEmail("email@test.com");

        when(passwordEncoder.encode(loginDTO.getSenha())).thenReturn(encodedPassword);
        when(usuarioRepository.findByEmailAndSenha(loginDTO.getEmail(), encodedPassword)).thenReturn(Optional.of(usuario));

        Usuario result = usuarioService.getUsuarioByLogin(loginDTO);

        assertNotNull(result);
        assertEquals("email@test.com", result.getEmail());
        verify(usuarioRepository).findByEmailAndSenha(loginDTO.getEmail(), encodedPassword);
    }

    @Test
    void entrarNaFila_shouldSaveUsuarioWithAtracao() {
        var requestDTO = new EntrarFilaRequestDTO();
        var atracao = new Atracao();
        var usuario = new Usuario();
        usuario.setId(1L);

        atracao.setId(2L);
        requestDTO.setAtracaoId(2L);
        requestDTO.setUsuario(usuario);

        when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.of(usuario));
        when(atracaoRepository.findById(atracao.getId())).thenReturn(Optional.of(atracao));
        doNothing().when(componentConfig).AdicionarUsuarioHashMp(requestDTO.getAtracaoId(), usuario);

        usuarioService.entrarNaFila(requestDTO);

        assertEquals(atracao, usuario.getAtracao());
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void sairDaFila_shouldRemoveUsuarioFromAtracao() {
        var atracaoId = 1L;
        var usuario = new Usuario();
        usuario.setId(10L);

        when(componentConfig.getInicio(atracaoId)).thenReturn(new ComponentConfig.QueueUser(usuario));
        when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.of(usuario));
        doNothing().when(componentConfig).RemoverUsuarioFila(atracaoId);

        usuarioService.sairDaFila(atracaoId);

        assertNull(usuario.getAtracao());
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void posicaoDaFila_shouldReturnPosition() {
        var requestDTO = new EntrarFilaRequestDTO();
        var usuario = new Usuario();
        usuario.setId(1L);

        requestDTO.setAtracaoId(2L);
        requestDTO.setUsuario(usuario);

        when(componentConfig.ObterPosicaoFila(requestDTO.getAtracaoId(), usuario)).thenReturn(5);

        int position = usuarioService.posicaoDaFila(requestDTO);

        assertEquals(5, position);
        verify(componentConfig).ObterPosicaoFila(requestDTO.getAtracaoId(), usuario);
    }

    @Test
    void findById_shouldReturnUsuarioDTO_whenUsuarioExists() {
        var usuario = new Usuario();
        usuario.setId(1L);

        when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.of(usuario));

        UsuarioDTO result = usuarioService.findById(usuario.getId());

        assertNotNull(result);
        assertEquals(usuario.getId(), result.getId());
        verify(usuarioRepository).findById(usuario.getId());
    }
}
