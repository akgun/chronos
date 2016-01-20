package com.akgund.chronos.core.impl;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathService implements IPathService {

    @Override
    public Path getPath(String uri) throws ChronosCoreException {
        return Paths.get(uri);
    }

    @Override
    public boolean exists(Path path) {
        return Files.exists(path);
    }

    @Override
    public void createFile(Path path) throws IOException {
        Files.createDirectories(path.getParent());
        Files.createFile(path);
    }
}
