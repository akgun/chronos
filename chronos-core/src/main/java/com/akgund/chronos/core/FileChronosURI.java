package com.akgund.chronos.core;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileChronosURI implements IChronosURI {

    @Override
    public String getURI() throws URISyntaxException {
        Path path = Paths.get(userHome(), chronosFolder(), chronosDataFile());

        return path.toString();
    }

    public String userHome() {
        return System.getProperty("user.home");
    }

    public String chronosFolder() {
        return ".chronos";
    }

    public String chronosDataFile() {
        return "chronos.json";
    }
}
