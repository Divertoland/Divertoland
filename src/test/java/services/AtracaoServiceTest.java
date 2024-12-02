package services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.websocket.divertoland.domain.Atracao;
import com.websocket.divertoland.infrastructure.abstractions.repositories.AtracaoRepository;
import com.websocket.divertoland.services.external.AzureBlobService;
import com.websocket.divertoland.services.v1.AtracaoServiceV1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.util.List;

class AtracaoServiceV1Test {

    @Mock
    private AtracaoRepository atracaoRepository;

    @Mock
    private AzureBlobService azureBlobService;

    @InjectMocks
    private AtracaoServiceV1 atracaoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listAtracoesAsync_shouldProcessImages() throws Exception {
        var atracao = new Atracao();
        atracao.setImgName("image.png");
        atracao.setNome("Test Atracao");

        when(atracaoRepository.findAll()).thenReturn(List.of(atracao));
        when(azureBlobService.downloadFile(anyString(), eq("image.png"))).thenReturn(new ByteArrayInputStream("imageData".getBytes()));

        List<Atracao> result = atracaoService.listAtracoesAsync();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(Base64.getEncoder().encodeToString("imageData".getBytes()), result.get(0).getImagemBase64());
    }
}
