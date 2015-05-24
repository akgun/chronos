package com.akgund.chronos.test;

import com.akgund.chronos.model.Task;
import com.akgund.chronos.model.Work;
import com.akgund.chronos.service.IChronosService;
import com.akgund.chronos.util.CDIFactory;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestIChronosService {

    @Test
    public void testListTasks() {
        IChronosService chronosService = CDIFactory.getInstance().inject(IChronosService.class);

        List<Task> tasks = chronosService.listTasks();

        assertNotNull(tasks);
        assertEquals(1, tasks.size());

        Task task1 = tasks.get(0);
        assertEquals(1L, (long) task1.getId());
        assertEquals("task1", task1.getName());

        List<Work> workList = task1.getWorkList();
        assertNotNull(workList);
        assertEquals(1, workList.size());

        Work work1 = workList.get(0);
        assertEquals(1L, (long) work1.getId());
        assertEquals("comment1", work1.getComment());
    }
}
