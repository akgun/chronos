package com.akgund.chronos.service;

import com.akgund.chronos.core.impl.ChronosCoreException;
import com.akgund.chronos.model.settings.Settings;

import java.io.Serializable;
import java.net.URISyntaxException;

public interface IChronosSettingsService extends Serializable {

    Settings getSettings() throws ChronosCoreException;

    void saveSettings(Settings settings) throws ChronosCoreException;

    String getSettingsUri() throws URISyntaxException;
}
