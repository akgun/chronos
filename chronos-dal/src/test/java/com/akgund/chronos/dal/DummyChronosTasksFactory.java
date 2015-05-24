package com.akgund.chronos.dal;

import com.akgund.chronos.model.ChronosTasks;
import com.akgund.chronos.model.Task;
import com.akgund.chronos.model.Work;
import hirondelle.date4j.DateTime;

/**
 * Created by akgun on 24.5.2015.
 */
public class DummyChronosTasksFactory {

    public static ChronosTasks createDummy(){
        ChronosTasks chronosTasks = new ChronosTasks();

        Task task = new Task();
        task.setActive(true);
        task.setName("task1");
        task.setId(1L);
        chronosTasks.getAllTasks().add(task);

        Work work = new Work();
        work.setId(1L);
        work.setComment("comment1");
        work.setStart(new DateTime("2015-01-01"));
        work.setEnd(new DateTime("2015-01-02"));
        task.getWorkList().add(work);

        return chronosTasks;
    }
}
