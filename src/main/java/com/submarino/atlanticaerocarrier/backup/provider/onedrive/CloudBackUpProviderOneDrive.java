package com.submarino.atlanticaerocarrier.backup.provider.onedrive;

import com.submarino.atlanticaerocarrier.backup.provider.AtlanticAerocarrierCloudBackupProvider;

import java.nio.file.Path;
import java.util.List;

public class CloudBackUpProviderOneDrive implements AtlanticAerocarrierCloudBackupProvider {
    @Override
    public void upload(Path arquivo, String destino) {

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
