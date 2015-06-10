package com.akgund.chronos.service;

import com.akgund.chronos.core.ChronosCoreException;
import com.akgund.chronos.core.IChronosTasksDAL;
import com.akgund.chronos.model.*;
import com.akgund.chronos.model.report.DateReport;
import com.akgund.chronos.model.report.WorkReport;
import com.google.inject.Inject;
import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.util.ArrayList;
import java.util.Collections;
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
    public FilterWorkResponse filterWorks(Long taskId, FilterWorkRequest filterWorkRequest)
            throws ChronosCoreException, ChronosServiceException {
        Task task = getTask(taskId);
        if (task == null) {
            throw new ChronosServiceException("Task not found. Task id: " + taskId);
        }

        List<Work> works = task.getWorkList().stream().filter(work -> {
            if (filterWorkRequest.getYear() == null) {
                return true;
            }

            return work.getStart().getYear() == filterWorkRequest.getYear();
        }).filter(work -> {
                    if (filterWorkRequest.getMonth() == null) {
                        return true;
                    }

                    return work.getStart().getMonthOfYear() == filterWorkRequest.getMonth();
                }
        ).filter(work -> {
            if (filterWorkRequest.getDay() == null) {
                return true;
            }

            return work.getStart().getDayOfMonth() == filterWorkRequest.getDay();
        }).collect(Collectors.toList());

        Duration total = Duration.millis(0);

        for (Work work : works) {
            DateTime start = work.getStart();
            DateTime end = work.getEnd();
            if (end == null && task.isActive()) {
                end = DateTime.now();
            }

            total = total.plus(new Duration(start, end));
        }

        FilterWorkResponse filterWorkResult = new FilterWorkResponse();
        filterWorkResult.setWorkList(works);
        filterWorkResult.setTotalDuration(total);

        return filterWorkResult;
    }

    @Override
    public void saveTask(Task task) throws ChronosCoreException {
        ChronosTasks chronosTasks = chronosTasksDAL.get();

        /* New record. */
        if (task.getId() == null) {
            task.setId(generateId());
        }

        task.getWorkList().stream().forEach(work -> {
            if (work.getId() == null) {
                work.setId(generateId());
            }
            work.setStart(normalizeDateTime(work.getStart()));
            work.setEnd(normalizeDateTime(work.getEnd()));
        });
        Collections.sort(task.getWorkList(), new WorkComparator());

        chronosTasks.getTasks().put(task.getId(), task);
        chronosTasksDAL.save(chronosTasks);
    }

    @Override
    public void saveWork(Work work) throws ChronosServiceException, ChronosCoreException {
        if (work == null) {
            throw new ChronosServiceException("Work is null.");
        }

        if (work.getTaskId() == null) {
            throw new ChronosServiceException("Task id of work is null.");
        }

        Task task = getTask(work.getTaskId());

        work.setStart(normalizeDateTime(work.getStart()));
        work.setEnd(normalizeDateTime(work.getEnd()));

        if (work.getId() != null) {
            Work existingWork = task.getWorkList().stream().filter(w -> w.getId().equals(work.getId())).findFirst().get();
            existingWork.setStart(work.getStart());
            existingWork.setEnd(work.getEnd());
            existingWork.setComment(work.getComment());
        } else {
            work.setId(generateId());
            /* new work. */
            task.getWorkList().add(work);
        }


        saveTask(task);
    }

    @Override
    public void deleteWork(Long taskId, Long workId) throws ChronosCoreException, ChronosServiceException {
        Task task = getTask(taskId);

        if (task == null) {
            throw new ChronosServiceException(String.format("Task not found. Task id: %s", taskId));
        }

        boolean deleted = task.getWorkList().removeIf(work -> work.getId().equals(workId));
        if (!deleted) {
            throw new ChronosServiceException(String.format("Delete failed. Task id: %s, work id: %s.", taskId, workId));
        }

        saveTask(task);
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
        newWork.setId(generateId());
        newWork.setTaskId(task.getId());
        newWork.setStart(normalizeDateTime(DateTime.now()));
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
                lastWork.setEnd(normalizeDateTime(DateTime.now()));
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

    private DateTime normalizeDateTime(DateTime dateTime) {
        if (dateTime == null) {
            return null;
        }

        return dateTime.withSecondOfMinute(0).withMillisOfSecond(0);
    }

    @Override
    public DateReport getReport(FilterWorkRequest filterWorkRequest) throws ChronosCoreException {
        List<Task> tasks = listTasks();

        DateReport dateReport = new DateReport();

        tasks.stream().forEach(task -> {

            List<Work> works = task.getWorkList().stream()
                    .filter(work -> {
                        if (filterWorkRequest.getYear() != null) {
                            return work.getStart().getYear() == filterWorkRequest.getYear();
                        }

                        return true;
                    })
                    .filter(work -> {
                        if (filterWorkRequest.getMonth() != null) {
                            return work.getStart().getMonthOfYear() == filterWorkRequest.getMonth();
                        }

                        return true;
                    })
                    .filter(work -> {
                        if (filterWorkRequest.getDay() != null) {
                            return work.getStart().getDayOfMonth() == filterWorkRequest.getDay();
                        }

                        return true;
                    }).collect(Collectors.toList());

            works.forEach(work -> {
                WorkReport workReport = new WorkReport();
                workReport.setWork(work);
                workReport.setTaskName(task.getName());

                dateReport.getWorkReportList().add(workReport);
            });

        });

        /* Sort reports. */
        Collections.sort(dateReport.getWorkReportList(), new WorkReportComparator());

        return dateReport;
    }
}
