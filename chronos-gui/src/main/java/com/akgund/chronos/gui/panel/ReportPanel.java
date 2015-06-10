package com.akgund.chronos.gui.panel;

import com.akgund.chronos.ChronosServiceFactory;
import com.akgund.chronos.core.ChronosCoreException;
import com.akgund.chronos.gui.widget.DateTimeSelector;
import com.akgund.chronos.gui.widget.ReportTableModel;
import com.akgund.chronos.model.FilterWorkRequest;
import com.akgund.chronos.service.IChronosService;
import org.joda.time.DateTime;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class ReportPanel extends JPanel {
    private IChronosService chronosService = ChronosServiceFactory.create();
    private DateTimeSelector dateTimeSelector;
    private final JTable reportTable;
    private ReportTableModel reportTableModel;

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

        loadTableData(DateTime.now().getMonthOfYear(), DateTime.now().getDayOfMonth());
    }

    private void loadTableData(int month, int day) {
        FilterWorkRequest filterWorkRequest = new FilterWorkRequest();
        filterWorkRequest.setMonth(month);
        filterWorkRequest.setDay(day);

        try {
            reportTableModel.setDateReport(chronosService.getReport(filterWorkRequest));
            reportTableModel.fireTableDataChanged();
        } catch (ChronosCoreException e) {
            e.printStackTrace();
        }
    }
}
