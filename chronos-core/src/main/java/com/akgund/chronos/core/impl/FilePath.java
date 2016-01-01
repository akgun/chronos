package com.akgund.chronos.core.impl;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FilePath {
    private UserHomeProvider userHomeProvider = new UserHomeProvider();

    public Path getDataPath() {
        return getPath(chronosDataFile());
    }

    public Path getSettingsPath() {
        return getPath(chronosSettingsFile());
    }

    private Path getPath(String file) {
        return Paths.get(userHome(), chronosFolder(), file);
    }

    private String userHome() {
        return userHomeProvider.getUserHome();
    }

    private String chronosFolder() {
        return ".chronos";
    }

    private String chronosDataFile() {
        return "data.json";
    }

    private String chronosSettingsFile() {
        return "settings.json";
    }
}
