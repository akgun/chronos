package com.akgund.chronos.model.report;

import java.util.ArrayList;
import java.util.List;

public class DateReport {
    private List<WorkReport> workReportList;

    public DateReport() {
        this.workReportList = new ArrayList<>();
    }

    public List<WorkReport> getWorkReportList() {
        return workReportList;
    }

    public void setWorkReportList(List<WorkReport> workReportList) {
        this.workReportList = workReportList;
    }
}
