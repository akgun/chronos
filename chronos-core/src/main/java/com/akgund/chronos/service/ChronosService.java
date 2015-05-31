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
import java.util.stream.Collectors;

public class ChronosService implements IChronosService {

    @Inject
    private IChronosTasksDAL chronosTasksDAL;

    @Override
    public List<Task> listTasks() throws ChronosCoreException {
        ChronosTasks chronosTasks = chronosTasksDAL.get();
        return new ArrayList<>(chronosTasks.getTasks().values());
    }

    @Override
    public List<Work> filterWorks(Long taskId, Integer year, Integer month, Integer day)
            throws ChronosCoreException, ChronosServiceException {
        Task task = getTask(taskId);
        if (task == null) {
            throw new ChronosServiceException("Task not found. Task id: " + taskId);
        }

        List<Work> works = task.getWorkList().stream().filter(work1 -> {
            if (year == null) {
                return true;
            }

            return work1.getStart().getYear() == year;
        }).filter(work2 -> {
                    if (month == null) {
                        return true;
                    }

                    return work2.getStart().getMonthOfYear() == month;
                }
        ).filter(work3 -> {
            if (day == null) {
                return true;
            }

            return work3.getStart().getDayOfMonth() == day;
        }).collect(Collectors.toList());


        return works;
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
        Task activeTask = findActiveTask();
        if (activeTask != null) {
            if (taskId == activeTask.getId()) {
                /* Active already. */
                return;
            }
        }

        deactivateActiveTask();

        final Task task = getTask(taskId);
        if (task == null) {
            throw new ChronosServiceException("Could not find task. Task id: " + taskId);
        }

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
