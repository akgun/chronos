package com.akgund.chronos.model;

import org.joda.time.Duration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FilterWorkResponse implements Serializable {
    private List<Work> workList;
    private Duration totalDuration;

    public FilterWorkResponse() {
        workList = new ArrayList<>();
    }

    public List<Work> getWorkList() {
        return workList;
    }

    public void setWorkList(List<Work> workList) {
        this.workList = workList;
    }

    public Duration getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(Duration totalDuration) {
        this.totalDuration = totalDuration;
    }
}
