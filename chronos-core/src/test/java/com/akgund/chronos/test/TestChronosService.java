package com.akgund.chronos.test;

import com.akgund.chronos.core.ChronosCoreException;
import com.akgund.chronos.model.Task;
import com.akgund.chronos.model.Work;
import com.akgund.chronos.service.ChronosServiceException;
import org.joda.time.DateTime;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TestChronosService extends BaseTest {

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

    @Test
    public void testDeleteWork() throws ChronosCoreException, ChronosServiceException {
        long taskId = 1433088710417L;
        chronosService.deleteWork(taskId, 1433088713499L);

        assertTrue(chronosService.getTask(taskId).getWorkList().isEmpty());
    }
}
