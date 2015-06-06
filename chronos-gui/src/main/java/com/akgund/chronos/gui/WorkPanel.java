package com.akgund.chronos.gui;

import com.akgund.chronos.ChronosServiceFactory;
import com.akgund.chronos.core.ChronosCoreException;
import com.akgund.chronos.model.Work;
import com.akgund.chronos.service.ChronosServiceException;
import com.akgund.chronos.service.IChronosService;
import com.akgund.chronos.util.DateTimeHelper;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Period;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class WorkPanel extends JPanel implements ActionListener {
    private static final int ALL_SELECTION = -1;
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

    private List<Work> getWorks(Long taskId, int selectedMonth, int day) {
        try {
            return chronosService.filterWorks(taskId, DateTime.now().getYear(),
                    selectedMonth > ALL_SELECTION ? selectedMonth : null, day > ALL_SELECTION ? day : null);
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

        jPanel.add(new JLabel("Month: "));
        comboMonthSelection.addItem(new WorkDaySelectionItem(ALL_SELECTION));
        IntStream.range(1, 13).forEach(value ->
                comboMonthSelection.addItem(new WorkDaySelectionItem(value)));
        comboMonthSelection.setSelectedIndex(DateTime.now().getMonthOfYear());
        jPanel.add(comboMonthSelection);

        jPanel.add(new JLabel("Day: "));
        comboDaySelection.addItem(new WorkDaySelectionItem(ALL_SELECTION));
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
        final int selectedMonth = getSelectedMonth();
        final int selectedDay = getSelectedDay();

        final List<Work> works = getWorks(taskId, selectedMonth, selectedDay);

        tableWorkList.setModel(new DefaultTableModel(createTableData(works), columns));
        try {
            Duration dailyTotalWork = chronosService.getTotalWork(taskId,
                    work -> work.getStart().getYear() == DateTime.now().getYear()
                            && selectedMonth > ALL_SELECTION ? work.getStart().getMonthOfYear() == selectedMonth : true
                            && selectedDay > ALL_SELECTION ? work.getStart().getDayOfMonth() == selectedDay : true);
            String dailyTask = String.format("Total: %s",
                    DateTimeHelper.printDuration(dailyTotalWork.toPeriod()));
            labelDailyTask.setText(dailyTask);
        } catch (ChronosCoreException e) {
            e.printStackTrace();
        }
    }

    private int getSelectedMonth() {
        Object selectedItem = comboMonthSelection.getSelectedItem();
        if (selectedItem == null) {
            return ALL_SELECTION;
        }

        return ((WorkDaySelectionItem) selectedItem).getValue();
    }

    private int getSelectedDay() {
        Object selectedItem = comboDaySelection.getSelectedItem();
        if (selectedItem == null) {
            return ALL_SELECTION;
        }

        return ((WorkDaySelectionItem) selectedItem).getValue();
    }

    class WorkDaySelectionItem extends AbstractComboBoxItem<Integer> {

        public WorkDaySelectionItem(Integer value) {
            super(value);
        }

        @Override
        public String getLabel() {
            if (getValue() == ALL_SELECTION) {
                return "All";
            }

            return String.format("%02d", getValue());
        }
    }
}