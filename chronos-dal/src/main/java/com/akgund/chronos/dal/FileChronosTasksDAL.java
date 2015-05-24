package com.akgund.chronos.dal;

import com.akgund.chronos.dal.serializer.IChronosSerializer;
import com.akgund.chronos.model.ChronosTasks;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileChronosTasksDAL implements IChronosTasksDAL {
    @Inject
    private IChronosSerializer chronosSerializer;
    @Inject
    private IChronosURI chronosURI;

    @Override
    public ChronosTasks get() throws ChronosDALException {
        String uri = chronosURI.getURI();
        String content;

        try {
            content = new String(Files.readAllBytes(Paths.get(uri)));
        } catch (IOException e) {
            throw new ChronosDALException("Couldn't read file content.", e);
        }

        return chronosSerializer.deserialize(content);
    }

    @Override
    public void save(ChronosTasks chronosTasks) throws ChronosDALException {
        String data = chronosSerializer.serialize(chronosTasks);
        String uri = chronosURI.getURI();

        try {
            Files.write(Paths.get(uri), data.getBytes());
        } catch (IOException e) {
            throw new ChronosDALException("Couldn't write to file.", e);
        }
    }
}
