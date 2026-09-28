package com.submarino.atlanticaerocarrier;

import com.submarino.atlanticaerocarrier.backup.provider.onedrive.TolkenService.MicrosoftTokenService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MicrosoftTokenTest
        implements CommandLineRunner {

    private final MicrosoftTokenService tokenService;

    public MicrosoftTokenTest(
            MicrosoftTokenService tokenService
    ) {
        this.tokenService = tokenService;
    }

    @Override
    public void run(String... args) {
        try {
            String token =
                    tokenService.getAccessToken();

            System.out.println(
                    "Token recebido: "
                            + token.substring(0, 20)
                            + "..."
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
