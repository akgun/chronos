package com.akgund.chronos.core.impl;

import com.akgund.chronos.core.IChronosURI;

import java.net.URISyntaxException;

public class FileChronosURI implements IChronosURI {
    private FilePath filePath = new FilePath();

    @Override
    public String getDataURI() throws URISyntaxException {
        return filePath.getDataPath().toString();
    }

    @Override
    public String getSettingsURI() throws URISyntaxException {
        return filePath.getSettingsPath().toString();
    }
}
