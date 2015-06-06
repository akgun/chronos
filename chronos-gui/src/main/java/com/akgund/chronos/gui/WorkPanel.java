package com.akgund.chronos.gui;

import com.akgund.chronos.ChronosServiceFactory;
import com.akgund.chronos.core.ChronosCoreException;
import com.akgund.chronos.model.FilterWorkRequest;
import com.akgund.chronos.model.FilterWorkResponse;
import com.akgund.chronos.model.Work;
import com.akgund.chronos.service.ChronosServiceException;
import com.akgund.chronos.service.IChronosService;
import com.akgund.chronos.util.DateTimeHelper;
import org.joda.time.DateTime;
import org.joda.time.Period;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.IntStream;

public class WorkPanel extends JPanel implements ActionListener {
    private IChronosService chronosService = ChronosServiceFactory.create();
    private JComboBox<WorkDaySelectionItem> comboMonthSelection = new JComboBox<>();
    private JComboBox<WorkDaySelectionItem> comboDaySelection = new JComboBox<>();
    private static final String[] columns = new String[]{"Start", "End", "Duration"};
    private JTable tableWorkList = new JTable();
    private Long taskId;
    private JLabel labelDailyTask = new JLabel();

    public WorkPanel(Long taskId) {
        setLayout(new BorderLayout());
        this.taskId = taskId;
        initHandlers();

        createLayout();
    }

    private void initHandlers() {
        comboDaySelection.addActionListener(this);
        comboMonthSelection.addActionListener(this);
    }


    private void createLayout() {
        add(createDaySelectionPanel(), BorderLayout.NORTH);

        tableWorkList.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(tableWorkList);
        add(scrollPane, BorderLayout.CENTER);
        add(labelDailyTask, BorderLayout.SOUTH);
    }

    private JPanel createDaySelectionPanel() {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.X_AXIS));

        jPanel.add(new JLabel("Month: "));
        comboMonthSelection.addItem(new WorkDaySelectionItem(null));
        IntStream.range(1, 13).forEach(value ->
                comboMonthSelection.addItem(new WorkDaySelectionItem(value)));
        comboMonthSelection.setSelectedIndex(DateTime.now().getMonthOfYear());
        jPanel.add(comboMonthSelection);

        jPanel.add(new JLabel("Day: "));
        comboDaySelection.addItem(new WorkDaySelectionItem(null));
        IntStream.range(1, 32).forEach(value ->
                comboDaySelection.addItem(new WorkDaySelectionItem(value)));
        comboDaySelection.setSelectedIndex(DateTime.now().getDayOfMonth());
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

    @Override
    public void actionPerformed(ActionEvent event) {
        final Integer selectedMonth = getSelectedMonth();
        final Integer selectedDay = getSelectedDay();

        FilterWorkRequest filterWorkRequest = new FilterWorkRequest();
        filterWorkRequest.setMonth(selectedMonth);
        filterWorkRequest.setDay(selectedDay);

        try {
            FilterWorkResponse filterWorkResponse = chronosService.filterWorks(taskId, filterWorkRequest);
            tableWorkList.setModel(new DefaultTableModel(createTableData(filterWorkResponse.getWorkList()), columns));
            String dailyTask = String.format("Total: %s",
                    DateTimeHelper.printDuration(filterWorkResponse.getTotalDuration().toPeriod()));
            labelDailyTask.setText(dailyTask);
        } catch (ChronosCoreException e) {
            e.printStackTrace();
        } catch (ChronosServiceException e) {
            e.printStackTrace();
        }
    }

    private Integer getSelectedMonth() {
        Object selectedItem = comboMonthSelection.getSelectedItem();
        if (selectedItem == null) {
            return null;
        }

        return ((WorkDaySelectionItem) selectedItem).getValue();
    }

    private Integer getSelectedDay() {
        Object selectedItem = comboDaySelection.getSelectedItem();
        if (selectedItem == null) {
            return null;
        }

        return ((WorkDaySelectionItem) selectedItem).getValue();
    }

    class WorkDaySelectionItem extends AbstractComboBoxItem<Integer> {

        public WorkDaySelectionItem(Integer value) {
            super(value);
        }

        @Override
        public String getLabel() {
            if (getValue() == null) {
                return "All";
            }

            return String.format("%02d", getValue());
        }
    }
}