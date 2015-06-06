package com.akgund.chronos.service;

import com.akgund.chronos.core.ChronosCoreException;
import com.akgund.chronos.model.Task;
import com.akgund.chronos.model.Work;
import org.joda.time.Duration;

import java.util.List;
import java.util.function.Predicate;

public interface IChronosService {

    List<Task> listTasks() throws ChronosCoreException;

    List<Work> filterWorks(Long taskId, Integer year, Integer month, Integer day)
            throws ChronosCoreException, ChronosServiceException;

    Task getTask(Long id) throws ChronosCoreException;

    Task findActiveTask() throws ChronosCoreException;

    void saveTask(Task task) throws ChronosCoreException;

    void saveWork(Work work) throws ChronosServiceException, ChronosCoreException;

    void activateTask(Long taskId) throws ChronosCoreException, ChronosServiceException;

    void deactivateActiveTask() throws ChronosCoreException;

    Duration getTotalWork(Long taskId, Predicate<Work> predicate) throws ChronosCoreException;
}
