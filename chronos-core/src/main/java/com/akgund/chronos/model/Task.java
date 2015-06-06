package com.akgund.chronos.model;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Task {
    private Long id;
    private String name;
    private List<Work> workList;
    private boolean active;

    public Duration getTotalWork() {
        return getTotalWork(work -> true);
    }

    public Duration getTotalWork(Predicate<Work> predicate) {
        Duration total = Duration.millis(0);

        final List<Work> filteredWorks = getWorkList().stream().filter(predicate).collect(Collectors.toList());

        for (Work work : filteredWorks) {
            DateTime start = work.getStart();
            DateTime end = work.getEnd();
            if (end == null && isActive()) {
                end = DateTime.now();
            }

            total = total.plus(new Duration(start, end));
        }

        return total;
    }

    public Task() {
        workList = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Work> getWorkList() {
        return workList;
    }

    public void setWorkList(List<Work> workList) {
        this.workList = workList;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
