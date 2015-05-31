package com.akgund.chronos.gui;

import com.akgund.chronos.ChronosServiceFactory;
import com.akgund.chronos.core.ChronosCoreException;
import com.akgund.chronos.model.Work;
import com.akgund.chronos.service.ChronosServiceException;
import com.akgund.chronos.service.IChronosService;
import com.akgund.chronos.util.DateTimeHelper;
import org.joda.time.DateTime;
import org.joda.time.Period;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class WorkPanel extends JPanel {
    private IChronosService chronosService = ChronosServiceFactory.create();
    private JComboBox<Integer> comboDaySelection = new JComboBox<>();
    private static final String[] columns = new String[]{"Start", "End", "Duration"};
    private JTable tableWorkList = new JTable();
    private Long taskId;

    public WorkPanel(Long taskId) {
        setLayout(new BorderLayout());
        this.taskId = taskId;
        initHandlers();

        createLayout();
    }

    private void initHandlers() {
        comboDaySelection.addActionListener(e1 -> {
            Object selectedItem = comboDaySelection.getSelectedItem();
            if (selectedItem == null) {
                return;
            }

            int selectedDay = (int) selectedItem;
            List<Work> works = getWorks(taskId, selectedDay);

            tableWorkList.setModel(new DefaultTableModel(createTableData(works), columns));
        });
    }

    private void createLayout() {
        add(createDaySelectionPanel(), BorderLayout.NORTH);

        tableWorkList.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(tableWorkList);
        add(scrollPane, BorderLayout.CENTER);
    }

    private List<Work> getWorks(Long taskId, int day) {
        try {
            return chronosService.filterWorks(taskId, null, null, day > 0 ? day : null);
        } catch (ChronosCoreException e) {
            e.printStackTrace();
        } catch (ChronosServiceException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    private JPanel createDaySelectionPanel() {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.X_AXIS));
        IntStream.range(0, 32).forEach(value -> comboDaySelection.addItem(value));
        comboDaySelection.setSelectedItem(DateTime.now().getDayOfMonth());

        jPanel.add(new JLabel("Day: "));
        jPanel.add(comboDaySelection);

        return jPanel;
    }

    private Object[][] createTableData(List<Work> works) {
        if (works == null) {
            return new Object[][]{};
        }

        Object[][] data = new Object[works.size()][3];

        for (int i = 0; i < works.size(); i++) {
            DateTime start = works.get(i).getStart();
            DateTime end = works.get(i).getEnd();

            Period duration = Period.millis(-1);
            if (end != null) {
                duration = DateTimeHelper.getDiff(start, end);
            }

            data[i][0] = DateTimeHelper.printDate(start);
            data[i][1] = end != null ? DateTimeHelper.printDate(end) : "current";
            data[i][2] = DateTimeHelper.printDuration(duration);
        }

        return data;
    }
}
