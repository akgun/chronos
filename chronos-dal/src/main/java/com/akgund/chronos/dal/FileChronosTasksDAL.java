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
    public ChronosTasks get() {
        try {
            String uri = chronosURI.getURI();
            String content = new String(Files.readAllBytes(Paths.get(uri)));

            return chronosSerializer.deserialize(content);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void save(ChronosTasks chronosTasks) {
        String data = chronosSerializer.serialize(chronosTasks);
        String uri = chronosURI.getURI();

        try {
            Files.write(Paths.get(uri), data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
