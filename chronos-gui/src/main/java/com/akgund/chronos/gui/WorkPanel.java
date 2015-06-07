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

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.stream.IntStream;

public class WorkPanel extends JPanel implements ActionListener {
    private IChronosService chronosService = ChronosServiceFactory.create();
    private JComboBox<WorkDaySelectionItem> comboMonthSelection = new JComboBox<>();
    private JComboBox<WorkDaySelectionItem> comboDaySelection = new JComboBox<>();
    private JTable tableWorkList = new JTable();
    private Long taskId;
    private JLabel labelDailyTask = new JLabel();
    private WorkTableModel workTableModel = new WorkTableModel();
    private FilterWorkResponse filterWorkResponse;

    public WorkPanel(Long taskId) {
        setLayout(new BorderLayout());
        this.taskId = taskId;
        initHandlers();

        createLayout();
    }

    private void initHandlers() {
        comboDaySelection.addActionListener(this);
        comboMonthSelection.addActionListener(this);

        workTableModel.setWorkUpdateEvent(work -> {
            if (filterWorkResponse == null) {
                return;
            }

            try {
                chronosService.saveWork(work);
            } catch (ChronosServiceException e) {
                e.printStackTrace();
            } catch (ChronosCoreException e) {
                e.printStackTrace();
            }

            updateWorkFilter();
            updateTotalTaskLabel();
        });
    }

    private void createLayout() {
        add(createDaySelectionPanel(), BorderLayout.NORTH);

        tableWorkList.setFillsViewportHeight(true);
        tableWorkList.setModel(workTableModel);
        tableWorkList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(tableWorkList);
        add(scrollPane, BorderLayout.CENTER);

        JButton buttonDelete = new JButton("Delete Work");
        buttonDelete.addActionListener(e -> {
            int selectedRow = tableWorkList.getSelectedRow();
            if (selectedRow < 0) {
                return;
            }

            Work toDeleteWork = filterWorkResponse.getWorkList().get(selectedRow);
            try {
                try {
                    chronosService.deleteWork(toDeleteWork.getTaskId(), toDeleteWork.getId());
                } catch (ChronosServiceException e1) {
                    e1.printStackTrace();
                }

                refreshData();
            } catch (ChronosCoreException e1) {
                e1.printStackTrace();
            }
        });

        JPanel panelSouth = new JPanel();
        panelSouth.setLayout(new BoxLayout(panelSouth, BoxLayout.Y_AXIS));
        panelSouth.add(buttonDelete);
        panelSouth.add(labelDailyTask);
        add(panelSouth, BorderLayout.SOUTH);
    }

    private void refreshData() {
        updateWorkFilter();
        workTableModel.setWorkList(filterWorkResponse.getWorkList());
        workTableModel.fireTableDataChanged();
        updateTotalTaskLabel();
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

    @Override
    public void actionPerformed(ActionEvent event) {
        refreshData();
    }

    private void updateTotalTaskLabel() {
        String dailyTask = String.format("Total: %s",
                DateTimeHelper.printDuration(filterWorkResponse.getTotalDuration().toPeriod()));
        labelDailyTask.setText(dailyTask);
    }

    private void updateWorkFilter() {
        final Integer selectedMonth = getSelectedMonth();
        final Integer selectedDay = getSelectedDay();

        FilterWorkRequest filterWorkRequest = new FilterWorkRequest();
        filterWorkRequest.setMonth(selectedMonth);
        filterWorkRequest.setDay(selectedDay);

        try {
            filterWorkResponse = chronosService.filterWorks(taskId, filterWorkRequest);
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