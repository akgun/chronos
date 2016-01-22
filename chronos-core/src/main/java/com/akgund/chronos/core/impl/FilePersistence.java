package com.akgund.chronos.core.impl;

import com.akgund.chronos.core.IChronosURI;
import com.akgund.chronos.core.IPathService;
import com.akgund.chronos.core.IPersistence;
import com.google.inject.Inject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FilePersistence implements IPersistence {
    private IChronosURI chronosURI;
    private IPathService pathService;

    @Inject
    public FilePersistence(IChronosURI chronosURI, IPathService pathService) {
        this.chronosURI = chronosURI;
        this.pathService = pathService;
    }

    @Override
    public String read() throws ChronosCoreException {
        try {
            final String uri = chronosURI.getDataURI();
            final Path path = pathService.getPath(uri);

            if (!pathService.exists(path)) {
                throw new ChronosCoreException("File not found.");
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
            final Path path = pathService.getPath(uri);

            if (!pathService.exists(path)) {
                pathService.createFile(path);
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
