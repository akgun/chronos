package com.akgund.chronos.model;

import com.akgund.chronos.model.Task;

import java.util.Comparator;

public class TaskComparator implements Comparator<Task> {

    @Override
    public int compare(Task o1, Task o2) {
        if (o1 == null || o2 == null) {
            return -1;
        }
        Long id1 = o1.getId();
        Long id2 = o2.getId();

        if (id1 == null || id2 == null) {
            return -1;
        }

        return id1.compareTo(id2);
    }
}
