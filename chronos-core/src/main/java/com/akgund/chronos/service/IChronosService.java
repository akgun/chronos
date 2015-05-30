package com.akgund.chronos.service;

import com.akgund.chronos.core.ChronosCoreException;
import com.akgund.chronos.model.ChronosTasks;
import com.akgund.chronos.model.Task;

import java.util.List;

public interface IChronosService {

    List<Task> listTasks() throws ChronosCoreException;

    Task getTask(Long id) throws ChronosCoreException;

    Task findActiveTask() throws ChronosCoreException;

    void saveTask(Task task) throws ChronosCoreException;

    void activateTask(Long taskId) throws ChronosCoreException, ChronosServiceException;

    void deactivateActiveTask() throws ChronosCoreException;
}
