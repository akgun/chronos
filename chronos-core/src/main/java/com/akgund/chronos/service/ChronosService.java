package com.akgund.chronos.service;

import com.akgund.chronos.dal.ChronosDALException;
import com.akgund.chronos.dal.IChronosTasksDAL;
import com.akgund.chronos.model.ChronosTasks;
import com.akgund.chronos.model.Task;

import javax.inject.Inject;
import java.util.List;

public class ChronosService implements IChronosService {

    @Inject
    private IChronosTasksDAL chronosTasksDAL;

    @Override
    public List<Task> listTasks() {
        try {
            ChronosTasks chronosTasks = chronosTasksDAL.get();
            return chronosTasks.getAllTasks();
        } catch (ChronosDALException e) {
            e.printStackTrace();
        }

        return null;
    }
}
