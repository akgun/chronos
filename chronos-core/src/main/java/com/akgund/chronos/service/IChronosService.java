package com.akgund.chronos.service;

import com.akgund.chronos.core.ChronosCoreException;
import com.akgund.chronos.model.Task;

import java.util.List;

public interface IChronosService {

    Task getTask(Long id) throws ChronosCoreException;

    Task findActiveTask() throws ChronosCoreException;

    List<Task> listTasks() throws ChronosCoreException;

    void addTask(Task task) throws ChronosCoreException;

    void activateTask(Long taskId) throws ChronosCoreException, ChronosServiceException;
}
