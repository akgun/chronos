package com.akgund.chronos.service;

import com.akgund.chronos.model.Task;
import com.akgund.chronos.util.CDIFactory;
import org.junit.Test;

import java.util.List;

public class TestIChronosService {

    @Test
    public void listTasksTest() {
        IChronosService chronosService = CDIFactory.getInstance().inject(IChronosService.class);

        List<Task> tasks = chronosService.listTasks();
    }
}
