package com.akgund.chronos.model;

import java.util.HashMap;
import java.util.Map;

public class ChronosTasks {
    private Map<Long, Task> tasks;

    public ChronosTasks() {
        tasks = new HashMap<>();
    }

    public Map<Long, Task> getTasks() {
        return tasks;
    }

    public void setTasks(Map<Long, Task> tasks) {
        this.tasks = tasks;
    }
}
