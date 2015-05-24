package com.akgund.chronos.dal;

import com.akgund.chronos.model.ChronosTasks;
import com.akgund.chronos.model.Task;
import com.akgund.chronos.model.Work;
import com.akgund.chronos.util.CDIFactory;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestIChronosTasksDAL {

    @Test
    public void testGet() throws ChronosDALException {
        IChronosTasksDAL chronosTasksDAL = CDIFactory.getInstance().inject(IChronosTasksDAL.class);
        ChronosTasks chronosTasks = chronosTasksDAL.get();

        assertNotNull(chronosTasks);

        List<Task> allTasks = chronosTasks.getAllTasks();
        assertNotNull(allTasks);
        assertEquals(1, allTasks.size());

        Task task1 = allTasks.get(0);
        assertEquals("task1", task1.getName());
        assertEquals(1L, (long) task1.getId());

        List<Work> workList = task1.getWorkList();
        assertNotNull(workList);
        assertEquals(1, workList.size());

        Work work1 = workList.get(0);
        assertEquals(1L, (long) work1.getId());
        assertEquals("comment1", work1.getComment());

        /* TODO: more asserts. */
    }
}
