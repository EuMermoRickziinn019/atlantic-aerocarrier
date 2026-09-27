package com.submarino.atlanticaerocarrier.backup.provider;

import java.nio.file.Path;
import java.util.List;

public interface AtlanticAerocarrierCloudBackupProvider {
    public void upload(Path arquivo, String destino);
    public void download(String arquivoRemoto, Path destino);
    public void delete(String arquivoRemoto);
    public List<String> list();
    public String getProvider();
}
