package com.akgund.chronos.service;

import com.akgund.chronos.core.ChronosCoreException;
import com.akgund.chronos.core.IChronosTasksDAL;
import com.akgund.chronos.model.ChronosTasks;
import com.akgund.chronos.model.Task;
import com.akgund.chronos.model.Work;
import org.joda.time.DateTime;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class ChronosService implements IChronosService {

    @Inject
    private IChronosTasksDAL chronosTasksDAL;

    @Override
    public List<Task> listTasks() throws ChronosCoreException {
        ChronosTasks chronosTasks = chronosTasksDAL.get();
        return new ArrayList<>(chronosTasks.getTasks().values());
    }

    @Override
    public void saveTask(Task task) throws ChronosCoreException {
        ChronosTasks chronosTasks = chronosTasksDAL.get();

        /* New record. */
        if (task.getId() == null) {
            task.setId(generateId());
        }

        chronosTasks.getTasks().put(task.getId(), task);
        chronosTasksDAL.save(chronosTasks);
    }

    private long generateId() {
        return DateTime.now().getMillis();
    }

    @Override
    public void activateTask(Long taskId) throws ChronosCoreException, ChronosServiceException {
        final Task task = getTask(taskId);
        if (task == null) {
            throw new ChronosServiceException("Could not find task. Task id: " + taskId);
        }

        deactivateActiveTask();

        task.setActive(true);
        Work newWork = new Work();
        newWork.setStart(DateTime.now());
        task.getWorkList().add(newWork);

        saveTask(task);
    }

    @Override
    public void deactivateActiveTask() throws ChronosCoreException {
        final Task activeTask = findActiveTask();

        /* There is active task. */
        if (activeTask != null) {
            activeTask.setActive(false);
            List<Work> workList = activeTask.getWorkList();
            if (workList != null && !workList.isEmpty()) {
                Work lastWork = workList.get(workList.size() - 1);
                lastWork.setEnd(DateTime.now());
            }
            saveTask(activeTask);
        }
    }

    @Override
    public Task getTask(Long id) throws ChronosCoreException {
        ChronosTasks chronosTasks = chronosTasksDAL.get();
        return chronosTasks.getTasks().get(id);
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
