package com.akgund.chronos.core;

import java.net.URISyntaxException;

public interface IChronosURI {

    String getDataURI() throws URISyntaxException;

    String getSettingsURI() throws URISyntaxException;
}
