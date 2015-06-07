package com.akgund.chronos.test;

import com.akgund.chronos.core.ChronosCoreException;
import com.akgund.chronos.model.Task;
import com.akgund.chronos.model.Work;
import com.akgund.chronos.service.ChronosServiceException;
import org.joda.time.DateTime;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class TestChronosService extends BaseTest {

    @Test
    public void testListTasks() throws ChronosCoreException {
        List<Task> tasks = chronosService.listTasks();

        assertEquals(4, tasks.size());
    }

    @Test
    public void testSaveTak() throws ChronosCoreException {
        Task task = new Task();
        task.setName("new task");
        chronosService.saveTask(task);

        assertNotNull(task.getId());

        List<Task> tasks = chronosService.listTasks();
        assertEquals(5, tasks.size());

        List<Task> foundTasks = tasks.stream().filter(t -> t.getName().equals(task.getName())).collect(Collectors.toList());
        assertEquals(1, foundTasks.size());
        assertEquals(task.getId(), foundTasks.get(0).getId());
        assertEquals(task.getName(), foundTasks.get(0).getName());
    }

    @Test
    public void testSaveWork() throws ChronosCoreException, ChronosServiceException {
        long taskId = 1433694020636L;
        Task task = chronosService.getTask(taskId);
        Work work = task.getWorkList().get(0);
        DateTime end = new DateTime(2015, 10, 3, 12, 45, 0);
        work.setEnd(end);

        chronosService.saveWork(work);

        task = chronosService.getTask(taskId);
        Work updatedWork = task.getWorkList().get(0);
        assertEquals(updatedWork.getEnd(), end);
    }

    @Test
    public void testDeleteWork() throws ChronosCoreException, ChronosServiceException {
        long taskId = 1433694020636L;
        chronosService.deleteWork(taskId, 1433694244476L);

        assertTrue(chronosService.getTask(taskId).getWorkList().isEmpty());
    }
}
