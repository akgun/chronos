package com.akgund.chronos.core.impl;

import com.akgund.chronos.core.IChronosURI;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileChronosURI implements IChronosURI {

    @Override
    public String getDataURI() throws URISyntaxException {
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
        return "data.json";
    }
}
