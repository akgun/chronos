package com.akgund.chronos.core.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.nio.file.Path;

import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

public class FilePathTest {
    @Mock
    private UserHomeProvider userHomeProvider;
    @InjectMocks
    private FilePath filePath;

    @Before
    public void setUp() throws Exception {
        filePath = new FilePath();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetDataPath() throws Exception {
        when(userHomeProvider.getUserHome())
                .thenReturn("/home/akgun");

        final Path dataPath = filePath.getDataPath();

        assertThat(dataPath.toString(), endsWith("data.json"));
    }

    @Test
    public void testGetSettingsPath() throws Exception {
        when(userHomeProvider.getUserHome())
                .thenReturn("/home/akgun");

        final Path settingsPath = filePath.getSettingsPath();

        assertThat(settingsPath.toString(), endsWith("settings.json"));
    }
}