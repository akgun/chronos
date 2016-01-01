package com.akgund.chronos.core.impl;

import com.akgund.chronos.core.IChronosSerializer;
import com.akgund.chronos.core.IPersistence;
import com.akgund.chronos.model.ChronosTasks;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;


public class FileChronosTasksDALTest {
    @Mock
    private IChronosSerializer chronosSerializer;
    @Mock
    private IPersistence persistence;
    @InjectMocks
    private FileChronosTasksDAL fileChronosTasksDAL;

    @Before
    public void setup() {
        fileChronosTasksDAL = new FileChronosTasksDAL();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenPersistenceThrowsExceptionThenReturnEmptyObject() throws Exception {
        when(persistence.read()).thenThrow(ChronosCoreException.class);

        final ChronosTasks chronosTasks = fileChronosTasksDAL.get();

        assertTrue(chronosTasks.getTasks().isEmpty());
    }

    @Test
    public void whenPersistenceReturnsNullThenReturnEmptyObject() throws Exception {
        when(persistence.read()).thenReturn(null);

        final ChronosTasks chronosTasks = fileChronosTasksDAL.get();

        assertTrue(chronosTasks.getTasks().isEmpty());
    }

    @Test
    public void whenPersistenceReturnsEmptyStringThenReturnEmptyObject() throws Exception {
        when(persistence.read()).thenReturn("");

        final ChronosTasks chronosTasks = fileChronosTasksDAL.get();

        assertTrue(chronosTasks.getTasks().isEmpty());
    }

    @Test
    public void whenPersistenceReturnsEmptyStringWithSpaceThenReturnEmptyObject() throws Exception {
        when(persistence.read()).thenReturn("   ");

        final ChronosTasks chronosTasks = fileChronosTasksDAL.get();

        assertTrue(chronosTasks.getTasks().isEmpty());
    }
}