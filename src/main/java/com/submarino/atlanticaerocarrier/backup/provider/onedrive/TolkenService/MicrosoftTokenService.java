package com.submarino.atlanticaerocarrier.backup.provider.onedrive.TolkenService;

import com.microsoft.aad.msal4j.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class MicrosoftTokenService {
    private final String tenantId;
    private final String clientId;
    private final String clientSecret;

    public MicrosoftTokenService(
            @Value("${backup.onedrive.tenant-id}") String tenantId,
            @Value("${backup.onedrive.client-id}") String clientId,
            @Value("${backup.onedrive.client-secret}") String clientSecret
    ) {
        this.tenantId = tenantId;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public String getAccessToken() throws Exception {

        IClientCredential credential =
                ClientCredentialFactory.createFromSecret(clientSecret);

        ConfidentialClientApplication app = ConfidentialClientApplication
            .builder(clientId, credential)
            .authority(
                    "https://login.microsoftonline.com/" + tenantId
            )
            .build();

        ClientCredentialParameters parameters = ClientCredentialParameters
            .builder(
                    Set.of(
                            "https://graph.microsoft.com/.default"
                    )
            )
            .build();

        IAuthenticationResult result =
                app.acquireToken(parameters).get();

        return result.accessToken();
    }
}
