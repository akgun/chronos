package com.akgund.chronos.service;

import com.akgund.chronos.model.Work;

import java.util.Comparator;

public class WorkComparator implements Comparator<Work> {

    @Override
    public int compare(Work o1, Work o2) {
        if (o1 == null || o2 == null || o1.getStart() == null || o2.getStart() == null) {
            return -1;
        }

        return o1.getStart().compareTo(o2.getStart());
    }
}
