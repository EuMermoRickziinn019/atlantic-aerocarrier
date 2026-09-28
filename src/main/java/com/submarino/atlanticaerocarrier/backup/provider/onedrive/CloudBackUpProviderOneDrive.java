package com.submarino.atlanticaerocarrier.backup.provider.onedrive;

import com.submarino.atlanticaerocarrier.backup.provider.AtlanticAerocarrierCloudBackupProvider;
import com.submarino.atlanticaerocarrier.backup.provider.onedrive.TolkenService.MicrosoftTokenService;
import com.submarino.atlanticaerocarrier.db.connection.ISBNegocios.ISBNegociosBK;
import com.submarino.atlanticaerocarrier.db.connection.ISBServiceBK;
import com.submarino.atlanticaerocarrier.models.dto.OneDriveItemResponseDTO;
import com.submarino.httpClient.SubmarinoHttpClient;
import com.submarino.httpClient.SubmarinoTypeResponse;
import com.submarino.httpClient.impl.SubmarinoHttpClientMethods;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

@Component
public class CloudBackUpProviderOneDrive implements AtlanticAerocarrierCloudBackupProvider {
    private static final String GRAPH_URL =
            "https://graph.microsoft.com/v1.0";

    private final MicrosoftTokenService tokenService;
    private final String userId;
    private final SubmarinoHttpClient<OneDriveItemResponseDTO> httpClient;
    private final ISBServiceBK negociosBK;

    public CloudBackUpProviderOneDrive(
            MicrosoftTokenService tokenService,
            @Value("${backup.onedrive.user-id}") String userId
    ) {
        this.tokenService = tokenService;
        this.userId = userId;
        this.httpClient = new SubmarinoHttpClientMethods<OneDriveItemResponseDTO>(
                SubmarinoTypeResponse.JSON
        );
        this.negociosBK = new ISBNegociosBK();
    }

    @Override
    public void upload(Path arquivo, String pasta) {
        try {
            String token = tokenService.getAccessToken();
            Map<String, String> headers = Map.of(
                "Authorization", "Bearer " + token,
                "Content-Type", "application/octet-stream"
            );
            String fileName = arquivo.getFileName().toString();
            String url =
                    "https://graph.microsoft.com/v1.0"
                        + "/users/"
                        + userId
                        + "/drive/root:/"
                        + pasta
                        + "/"
                        + fileName
                        + ":/content";
            OneDriveItemResponseDTO resp =
                    this.httpClient.put(url, OneDriveItemResponseDTO.class, arquivo, headers);
            if(resp.getId() != null) {
                negociosBK.registerUpload();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void download(String arquivoRemoto, Path destino) {

    }

    @Override
    public void delete(String arquivoRemoto) {

    }

    @Override
    public List<String> list() {
        return List.of();
    }

    @Override
    public String getProvider() {
        return "";
    }
}
