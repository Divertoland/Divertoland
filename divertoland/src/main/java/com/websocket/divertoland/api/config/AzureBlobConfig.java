package com.websocket.divertoland.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;

@Configuration
public class AzureBlobConfig {

    @Value("${azure.blob.uri}")
    private String blobServiceUri;

    @Value("${azure.blob.sas}")
    private String sasToken;

    @Bean
    public BlobServiceClient blobServiceClient() {
        return new BlobServiceClientBuilder()
                .endpoint(blobServiceUri)
                .sasToken(sasToken)
                .buildClient();
    }
}