package com.akgund.chronos.gui.panel;

import com.akgund.chronos.ChronosServiceFactory;
import com.akgund.chronos.core.ChronosCoreException;
import com.akgund.chronos.gui.widget.DateTimeSelector;
import com.akgund.chronos.gui.widget.ReportTableModel;
import com.akgund.chronos.model.FilterWorkRequest;
import com.akgund.chronos.model.report.DateReport;
import com.akgund.chronos.model.report.WorkReport;
import com.akgund.chronos.service.IChronosService;
import com.akgund.chronos.util.DateTimeHelper;
import org.joda.time.DateTime;
import org.joda.time.Duration;

import javax.swing.*;
import java.awt.*;

public class ReportPanel extends JPanel {
    private IChronosService chronosService = ChronosServiceFactory.create();
    private DateTimeSelector dateTimeSelector;
    private final JTable reportTable;
    private ReportTableModel reportTableModel;
    private JLabel labelTotalDuration;

    public ReportPanel() {
        setLayout(new BorderLayout());

        reportTableModel = new ReportTableModel();
        reportTable = new JTable(reportTableModel);
        reportTable.setFillsViewportHeight(true);

        dateTimeSelector = new DateTimeSelector(true, false);
        dateTimeSelector.setDateTimeSelectionChange(dateTime -> {
            loadTableData(dateTime.getMonthOfYear(), dateTime.getDayOfMonth());
        });

        add(dateTimeSelector, BorderLayout.NORTH);
        JScrollPane scrollPane = new JScrollPane(reportTable);
        add(scrollPane, BorderLayout.CENTER);

        labelTotalDuration = new JLabel();
        add(labelTotalDuration, BorderLayout.SOUTH);

        loadTableData(DateTime.now().getMonthOfYear(), DateTime.now().getDayOfMonth());
    }

    private void loadTableData(int month, int day) {
        FilterWorkRequest filterWorkRequest = new FilterWorkRequest();
        filterWorkRequest.setMonth(month);
        filterWorkRequest.setDay(day);

        try {
            DateReport report = chronosService.getReport(filterWorkRequest);
            reportTableModel.setDateReport(report);
            reportTableModel.fireTableDataChanged();

            updateLabelTotalDuration(report);
        } catch (ChronosCoreException e) {
            e.printStackTrace();
        }
    }

    private void updateLabelTotalDuration(DateReport report) {
        try {
            /* TODO: ChronosService.filterWorks also does same thing. */
            Duration total = Duration.millis(0);

            for (WorkReport workReport : report.getWorkReportList()) {
                DateTime start = workReport.getWork().getStart();
                DateTime end = workReport.getWork().getEnd();

                total = total.plus(new Duration(start, end));
            }

            labelTotalDuration.setText(String.format("Total: %s", DateTimeHelper.printDuration(total.toPeriod())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
