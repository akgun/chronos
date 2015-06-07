package com.akgund.chronos.model.report;

import com.akgund.chronos.model.Work;

public class WorkReport {
    private Work work;
    private String taskName;

    public Work getWork() {
        return work;
    }

    public void setWork(Work work) {
        this.work = work;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}
