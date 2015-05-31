package com.akgund.chronos.core;


import com.akgund.chronos.model.ChronosTasks;
import com.google.inject.Inject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileChronosTasksDAL implements IChronosTasksDAL {
    @Inject
    private IChronosSerializer chronosSerializer;
    @Inject
    private IChronosURI chronosURI;

    @Override
    public ChronosTasks get() throws ChronosCoreException {
        String uri = chronosURI.getURI();
        String content;

        try {
            Path path = Paths.get(uri);

            /* Create file if does not exists. */
            if (!Files.exists(path)) {
                ChronosTasks chronosTasks = new ChronosTasks();
                save(chronosTasks);
            }

            content = new String(Files.readAllBytes(path));
        } catch (IOException e) {
            throw new ChronosCoreException("Couldn't read file content.", e);
        }
        ChronosTasks chronosTasks = chronosSerializer.deserialize(content);

        return chronosTasks;
    }

    @Override
    public void save(ChronosTasks chronosTasks) throws ChronosCoreException {
        String data = chronosSerializer.serialize(chronosTasks);
        String uri = chronosURI.getURI();

        try {
            Path path = Paths.get(uri);

            if (!Files.exists(path)) {
                Files.createDirectories(path.getParent());
                Files.createFile(path);
            }

            Files.write(path, data.getBytes(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new ChronosCoreException("Couldn't write to file.", e);
        }
    }
}
