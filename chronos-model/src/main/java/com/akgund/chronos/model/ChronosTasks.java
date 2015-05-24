package com.akgund.chronos.model;

import java.util.ArrayList;
import java.util.List;

public class ChronosTasks {
    private List<Task> allTasks;

    public ChronosTasks() {
        allTasks = new ArrayList<>();
    }

    public List<Task> getAllTasks() {
        return allTasks;
    }

    public void setAllTasks(List<Task> allTasks) {
        this.allTasks = allTasks;
    }
}
