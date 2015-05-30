package com.akgund.chronos.service;

import com.akgund.chronos.core.ChronosCoreException;
import com.akgund.chronos.core.IChronosTasksDAL;
import com.akgund.chronos.model.ChronosTasks;
import com.akgund.chronos.model.Task;
import com.akgund.chronos.model.Work;
import hirondelle.date4j.DateTime;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class ChronosService implements IChronosService {

    @Inject
    private IChronosTasksDAL chronosTasksDAL;

    @Override
    public List<Task> listTasks() throws ChronosCoreException {
        ChronosTasks chronosTasks = chronosTasksDAL.get();
        return chronosTasks.getAllTasks();
    }

    @Override
    public void addTask(Task task) throws ChronosCoreException {
        ChronosTasks chronosTasks = chronosTasksDAL.get();

        if (task.getId() == null) {
            task.setId(generateId(chronosTasks));
        }

        chronosTasks.getAllTasks().add(task);
        chronosTasksDAL.save(chronosTasks);
    }

    private long generateId(ChronosTasks chronosTasks) {
        if (chronosTasks == null || chronosTasks.getAllTasks() == null || chronosTasks.getAllTasks().isEmpty()) {
            return 1L;
        }

        return chronosTasks.getAllTasks().get(chronosTasks.getAllTasks().size() - 1).getId() + 1;
    }

    @Override
    public void activateTask(Long taskId) throws ChronosCoreException, ChronosServiceException {
        final ChronosTasks chronosTasks = chronosTasksDAL.get();

        final Task task = getTask(taskId);
        if (task == null) {
            throw new ChronosServiceException("Could not find task. Task id: " + taskId);
        }

        final Task activeTask = findActiveTask();
        if (activeTask == null) {
            throw new ChronosServiceException("Could not find active task.");
        }

        task.setActive(true);
        Work newWork = new Work();
        newWork.setStart(DateTime.now(TimeZone.getDefault()));
        task.getWorkList().add(newWork);

        activeTask.setActive(false);
        List<Work> workList1 = activeTask.getWorkList();
        if (workList1 != null && !workList1.isEmpty()) {
            Work lastWork = workList1.get(workList1.size() - 1);
            lastWork.setEnd(DateTime.now(TimeZone.getDefault()));
        }

        chronosTasksDAL.save(chronosTasks);
    }


    @Override
    public Task getTask(Long id) throws ChronosCoreException {
        List<Task> tasks = listTasks();

        for (Task t : tasks) {
            if (t.getId() == id) {
                return t;
            }
        }

        return null;
    }

    @Override
    public Task findActiveTask() throws ChronosCoreException {
        List<Task> tasks = listTasks();

        for (Task t : tasks) {
            if (t.isActive()) {
                return t;
            }
        }

        return null;
    }
}
