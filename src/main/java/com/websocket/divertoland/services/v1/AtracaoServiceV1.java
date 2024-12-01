package com.websocket.divertoland.services.v1;

import java.io.InputStream;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.websocket.divertoland.domain.Atracao;
import com.websocket.divertoland.infrastructure.abstractions.repositories.AtracaoRepository;
import com.websocket.divertoland.services.abstractions.AtracaoService;
import com.websocket.divertoland.services.external.AzureBlobService;

@Service
public class AtracaoServiceV1 implements AtracaoService {

    @Autowired
    private AtracaoRepository _atracaoRepository;

    @Autowired
    private AzureBlobService _azureBlobService;

    @Value("${azure.blob.image.container.atracoes}")
    private String _atracoesImageContainerName;

    @Async
    public CompletableFuture<List<Atracao>> listAtracoesAsync() { return CompletableFuture.supplyAsync(() ->
    {
        var atracoes = _atracaoRepository.findAll();
        for (Atracao atracao : atracoes) {
            try (InputStream blobStream = _azureBlobService.downloadFile(_atracoesImageContainerName, atracao.getImgName())) {
                atracao.setImagemBase64(Base64.getEncoder().encodeToString(blobStream.readAllBytes()));
            }
            catch(Exception ex){
                System.err.println("Não foi possível ler a imagem da atração: " + atracao.getNome());
            }
        }
        return atracoes;
    });}
}
