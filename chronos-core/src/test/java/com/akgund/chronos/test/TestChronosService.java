package com.akgund.chronos.test;

import com.akgund.chronos.core.ChronosCoreException;
import com.akgund.chronos.core.IChronosURI;
import com.akgund.chronos.model.Task;
import com.akgund.chronos.model.Work;
import com.akgund.chronos.service.ChronosServiceException;
import com.akgund.chronos.service.IChronosService;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestChronosService {
    private IChronosService chronosService = MockFactory.createChronosService();
    private IChronosURI chronosURI = MockFactory.createChoronosURI();
    private String originalData;

    @Before
    public void before() throws IOException {
        originalData = new String(Files.readAllBytes(Paths.get(chronosURI.getURI())));
    }

    @After
    public void after() throws IOException {
        Files.write(Paths.get(chronosURI.getURI()), originalData.getBytes());
    }

    @Test
    public void testListTasks() throws ChronosCoreException {
        List<Task> tasks = chronosService.listTasks();

        assertEquals(1, tasks.size());
        assertEquals("test task 1", tasks.get(0).getName());
    }

    @Test
    public void testSaveTak() throws ChronosCoreException {
        Task task = new Task();
        task.setName("test task 2");
        chronosService.saveTask(task);

        assertNotNull(task.getId());

        assertEquals(2, chronosService.listTasks().size());
    }

    @Test
    public void testSaveWork() throws ChronosCoreException, ChronosServiceException {
        long taskId = 1433088710417L;
        Task task = chronosService.getTask(taskId);
        Work work = task.getWorkList().get(0);
        DateTime end = new DateTime(2015, 10, 3, 12, 45, 3);
        work.setEnd(end);

        chronosService.saveWork(work);

        task = chronosService.getTask(taskId);
        Work updatedWork = task.getWorkList().get(0);
        assertEquals(updatedWork.getEnd(), end);
    }
}
