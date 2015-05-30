package com.akgund.chronos.gui;

import com.akgund.chronos.model.Task;
import com.akgund.chronos.service.IChronosService;
import com.akgund.chronos.util.CDIFactory;

public class Program {

    public static void main(String[] args) throws Exception {
        IChronosService chronosService = CDIFactory.getInstance().inject(IChronosService.class);
        Task task = new Task();
        task.setName("calisma");
        chronosService.saveTask(task);
        chronosService.activateTask(task.getId());
    }
}
