package com.akgund.chronos.model;

import com.akgund.chronos.model.report.WorkReport;

import java.util.Comparator;

public class WorkReportComparator implements Comparator<WorkReport> {

    @Override
    public int compare(WorkReport o1, WorkReport o2) {
        try {
            return o1.getWork().getStart().compareTo(o2.getWork().getStart());
        } catch (Exception e) {
            return 0;
        }
    }
}
