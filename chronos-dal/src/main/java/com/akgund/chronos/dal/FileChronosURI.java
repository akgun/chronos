package com.akgund.chronos.dal;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FileChronosURI implements IChronosURI {

    @Override
    public String getURI() {
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
        return "chronos.xml";
    }
}
