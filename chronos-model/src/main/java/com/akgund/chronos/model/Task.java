package com.akgund.chronos.model;

import java.util.ArrayList;
import java.util.List;

public class Task {
    private String name;
    private List<Work> workList;

    public Task() {
        workList = new ArrayList<>();
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
}
