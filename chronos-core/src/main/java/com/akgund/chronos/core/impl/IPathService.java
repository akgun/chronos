package com.akgund.chronos.core.impl;

import java.io.IOException;
import java.nio.file.Path;

public interface IPathService {

    Path getPath(String uri) throws ChronosCoreException;

    boolean exists(Path path);

    void createFile(Path path) throws IOException;
}
