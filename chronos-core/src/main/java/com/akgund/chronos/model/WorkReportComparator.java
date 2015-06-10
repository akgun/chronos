package com.akgund.chronos.model;

import com.akgund.chronos.model.report.WorkReport;

import java.util.Comparator;

public class WorkReportComparator implements Comparator<WorkReport> {

    @Override
    public int compare(WorkReport o1, WorkReport o2) {
        if (o1 == null || o1.getWork() == null || o1.getWork().getStart() == null
                || o2 == null || o2.getWork() == null || o2.getWork().getStart() == null) {
            return 0;
        }

        return o1.getWork().getStart().compareTo(o2.getWork().getStart());
    }
}
