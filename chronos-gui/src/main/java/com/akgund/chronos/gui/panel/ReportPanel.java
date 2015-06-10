package com.akgund.chronos.gui.panel;

import com.akgund.chronos.ChronosServiceFactory;
import com.akgund.chronos.core.ChronosCoreException;
import com.akgund.chronos.gui.widget.ReportTableModel;
import com.akgund.chronos.model.FilterWorkRequest;
import com.akgund.chronos.service.IChronosService;

import javax.swing.*;
import java.awt.*;

public class ReportPanel extends JPanel {
    private IChronosService chronosService = ChronosServiceFactory.create();
    private final JTable reportTable;
    private ReportTableModel reportTableModel;

    public ReportPanel() {
        setLayout(new BorderLayout());

        reportTableModel = new ReportTableModel();
        reportTable = new JTable(reportTableModel);
        reportTable.setFillsViewportHeight(true);

        loadTableData();

        JScrollPane scrollPane = new JScrollPane(reportTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadTableData() {
        FilterWorkRequest filterWorkRequest = new FilterWorkRequest();
        try {
            reportTableModel.setDateReport(chronosService.getReport(filterWorkRequest));
            reportTableModel.fireTableDataChanged();
        } catch (ChronosCoreException e) {
            e.printStackTrace();
        }
    }
}
