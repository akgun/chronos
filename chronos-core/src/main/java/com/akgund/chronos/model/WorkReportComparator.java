package com.akgund.chronos.model;

import com.akgund.chronos.model.report.WorkReport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;

public class WorkReportComparator implements Comparator<WorkReport> {
    private static final Logger logger = LogManager.getLogger(WorkReportComparator.class);

    @Override
    public int compare(WorkReport o1, WorkReport o2) {
        try {
            return o1.getWork().getStart().compareTo(o2.getWork().getStart());
        } catch (Exception e) {
            logger.debug(e.getMessage());
            return 0;
        }
    }
}
