package com.akgund.chronos.gui.widget;

import com.akgund.chronos.model.Work;
import com.akgund.chronos.model.report.DateReport;
import com.akgund.chronos.model.report.WorkReport;
import com.akgund.chronos.util.DateTimeHelper;
import org.joda.time.DateTime;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ReportTableModel extends AbstractTableModel {
    private static final String[] COLUMNS = {"Task", "Start", "End", "Duration"};
    private DateReport dateReport;
    private DateTimeHelper dateTimeHelper = DateTimeHelper.getInstance();

    public ReportTableModel() {
        this(new DateReport());
    }

    public ReportTableModel(DateReport dateReport) {
        this.dateReport = dateReport;
    }

    @Override
    public int getRowCount() {
        return dateReport.getWorkReportList().size();
    }

    @Override
    public int getColumnCount() {
        return COLUMNS.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        final List<WorkReport> workReportList = dateReport.getWorkReportList();

        if (workReportList == null || workReportList.isEmpty()) {
            return "";
        }

        WorkReport workReport = workReportList.get(rowIndex);
        Work work = workReport.getWork();
        if (work == null) {
            return "";
        }

        DateTime start = workReport.getWork().getStart();
        DateTime end = workReport.getWork().getEnd();
        switch (columnIndex) {
            case 0:
                return workReport.getTaskName();
            case 1:
                return dateTimeHelper.printDate(start);
            case 2:
                return dateTimeHelper.printDate(end);
            case 3:
                return dateTimeHelper.printDuration(dateTimeHelper.getDiff(start, end));
        }

        return "";
    }

    @Override
    public String getColumnName(int column) {
        return COLUMNS[column];
    }

    public DateReport getDateReport() {
        return dateReport;
    }

    public void setDateReport(DateReport dateReport) {
        this.dateReport = dateReport;
    }
}
