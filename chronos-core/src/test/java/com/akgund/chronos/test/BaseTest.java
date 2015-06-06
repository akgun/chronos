package com.akgund.chronos.test;

import com.akgund.chronos.core.IChronosURI;
import com.akgund.chronos.service.IChronosService;
import org.junit.After;
import org.junit.Before;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BaseTest {
    protected IChronosService chronosService = MockFactory.createChronosService();
    protected IChronosURI chronosURI = MockFactory.createChoronosURI();
    protected String originalData;

    @Before
    public void before() throws IOException {
        originalData = new String(Files.readAllBytes(Paths.get(chronosURI.getURI())));
    }

    @After
    public void after() throws IOException {
        Files.write(Paths.get(chronosURI.getURI()), originalData.getBytes());
    }
}
