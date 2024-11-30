package com.websocket.divertoland.services.external;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class AzureBlobService {

    @Autowired
    private BlobServiceClient _blobServiceClient;

    public void uploadFile(String containerName, String blobName, InputStream data, long dataSize) {

        _blobServiceClient.getBlobContainerClient(containerName)
            .getBlobClient(blobName)
            .upload(data, dataSize, true);
    }

    public InputStream downloadFile(String containerName, String blobName) {

        BlobClient blobClient = _blobServiceClient
            .getBlobContainerClient(containerName)
            .getBlobClient(blobName);

        if (blobClient.exists())
            return blobClient.openInputStream();
        else 
            throw new RuntimeException("Blob inexistente: " + blobName);
    }
}