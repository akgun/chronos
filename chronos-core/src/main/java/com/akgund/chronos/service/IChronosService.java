package com.akgund.chronos.service;

import com.akgund.chronos.core.ChronosCoreException;
import com.akgund.chronos.model.FilterWorkRequest;
import com.akgund.chronos.model.FilterWorkResponse;
import com.akgund.chronos.model.Task;
import com.akgund.chronos.model.Work;

import java.util.List;

public interface IChronosService {

    List<Task> listTasks() throws ChronosCoreException;

    FilterWorkResponse filterWorks(Long taskId, FilterWorkRequest filterWorkRequest)
            throws ChronosCoreException, ChronosServiceException;

    Task getTask(Long id) throws ChronosCoreException;

    Task findActiveTask() throws ChronosCoreException;

    void saveTask(Task task) throws ChronosCoreException;

    void saveWork(Work work) throws ChronosServiceException, ChronosCoreException;

    void deleteWork(Long taskId, Long workId) throws ChronosCoreException, ChronosServiceException;

    void activateTask(Long taskId) throws ChronosCoreException, ChronosServiceException;

    void deactivateActiveTask() throws ChronosCoreException;
}
