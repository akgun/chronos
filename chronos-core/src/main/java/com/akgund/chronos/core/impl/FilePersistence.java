package com.akgund.chronos.core.impl;

import com.akgund.chronos.core.IChronosURI;
import com.akgund.chronos.core.IPersistence;
import com.google.inject.Inject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FilePersistence implements IPersistence {
    private IChronosURI chronosURI;

    @Inject
    public FilePersistence(IChronosURI chronosURI) {
        this.chronosURI = chronosURI;
    }

    @Override
    public String read() throws ChronosCoreException {
        try {
            final String uri = chronosURI.getDataURI();
            final Path path = Paths.get(uri);

            /* Create file if does not exists. */
            if (!Files.exists(path)) {
                throw new ChronosCoreException("File not exists");
            }

            return new String(Files.readAllBytes(path));
        } catch (IOException e) {
            throw new ChronosCoreException("Couldn't read file content.", e);
        } catch (URISyntaxException e) {
            throw new ChronosCoreException(e.getMessage(), e);
        }
    }

    @Override
    public void write(String data) throws ChronosCoreException {
        try {
            final String uri = chronosURI.getDataURI();
            final Path path = Paths.get(uri);

            if (!Files.exists(path)) {
                Files.createDirectories(path.getParent());
                Files.createFile(path);
            }

            Files.write(path, data.getBytes(), StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new ChronosCoreException("Couldn't write to file.", e);
        } catch (URISyntaxException e) {
            throw new ChronosCoreException(e.getMessage(), e);
        }
    }
}
