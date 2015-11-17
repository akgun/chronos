package com.akgund.chronos.service;

import com.akgund.chronos.core.impl.ChronosCoreException;
import com.akgund.chronos.model.settings.Settings;

public interface IChronosSettingsService {

    Settings getSettings() throws ChronosCoreException;

    void saveSettings(Settings settings) throws ChronosCoreException;
}
