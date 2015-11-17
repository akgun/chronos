package com.akgund.chronos.core.impl;

import com.akgund.chronos.core.IChronosURI;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileChronosURI implements IChronosURI {

    @Override
    public String getDataURI() throws URISyntaxException {
        Path path = getPath(chronosDataFile());

        return path.toString();
    }

    @Override
    public String getSettingsURI() throws URISyntaxException {
        Path path = getPath(chronosSettingsFile());

        return path.toString();
    }

    public Path getPath(String file) {
        return Paths.get(userHome(), chronosFolder(), file);
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

    public String chronosSettingsFile() {
        return "settings.json";
    }
}
