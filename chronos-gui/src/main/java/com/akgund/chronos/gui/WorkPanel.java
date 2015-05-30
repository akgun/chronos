package com.akgund.chronos.gui;

import com.akgund.chronos.model.Work;
import com.akgund.chronos.util.DateTimeHelper;
import org.joda.time.DateTime;
import org.joda.time.Period;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class WorkPanel extends JPanel {

    public WorkPanel(List<Work> workList) {
        setLayout(new BorderLayout());
        String[] columns = new String[]{"Start", "End", "Duration"};

        JTable workTable = new JTable(createTableData(workList), columns);
        workTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
        workTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(workTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    private Object[][] createTableData(List<Work> works) {
        if (works == null) {
            return new Object[][]{};
        }

        Object[][] data = new Object[works.size()][3];

        for (int i = 0; i < works.size(); i++) {
            DateTime start = works.get(i).getStart();
            DateTime end = works.get(i).getEnd();
            Period duration = DateTimeHelper.getDiff(start, end);

            data[i][0] = DateTimeHelper.printDate(start);
            data[i][1] = DateTimeHelper.printDate(end);
            data[i][2] = DateTimeHelper.printDuration(duration);
        }

        return data;
    }
}
