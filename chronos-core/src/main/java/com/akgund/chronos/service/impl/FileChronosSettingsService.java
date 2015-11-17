package com.akgund.chronos.service.impl;

import com.akgund.chronos.core.IChronosSerializer;
import com.akgund.chronos.core.impl.ChronosCoreException;
import com.akgund.chronos.service.IChronosSettingsService;
import com.akgund.chronos.core.IChronosURI;
import com.akgund.chronos.model.settings.Settings;
import com.google.inject.Inject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileChronosSettingsService implements IChronosSettingsService {

    @Inject
    private IChronosURI chronosURI;
    @Inject
    private IChronosSerializer chronosSerializer;

    @Override
    public Settings getSettings() throws ChronosCoreException {
        String uri;
        try {
            uri = chronosURI.getSettingsURI();
        } catch (URISyntaxException e) {
            throw new ChronosCoreException(e.getMessage(), e);
        }
        String content;

        try {
            Path path = Paths.get(uri);

            /* Create file if does not exists. */
            if (!Files.exists(path)) {
                Settings settings = new Settings();
                saveSettings(settings);
            }

            content = new String(Files.readAllBytes(path));
        } catch (IOException e) {
            throw new ChronosCoreException("Couldn't read setting file content.", e);
        }

        return chronosSerializer.deserializeSettings(content);
    }

    @Override
    public void saveSettings(Settings settings) throws ChronosCoreException {
        String data = chronosSerializer.serializeSettings(settings);
        String uri;
        try {
            uri = chronosURI.getSettingsURI();
        } catch (URISyntaxException e) {
            throw new ChronosCoreException(e.getMessage(), e);
        }

        try {
            Path path = Paths.get(uri);

            if (!Files.exists(path)) {
                Files.createDirectories(path.getParent());
                Files.createFile(path);
            }

            Files.write(path, data.getBytes(), StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new ChronosCoreException("Couldn't write to settings file.", e);
        }
    }
}
