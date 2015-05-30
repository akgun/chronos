package com.akgund.chronos.gui;

import com.akgund.chronos.core.ChronosCoreException;
import com.akgund.chronos.model.Task;
import com.akgund.chronos.service.IChronosService;
import com.akgund.chronos.util.CDIFactory;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TasksPanel extends JPanel {
    private IChronosService chronosService = CDIFactory.getInstance().inject(IChronosService.class);
    private JComboBox<TaskComboBoxItem> comboBoxTasks = new JComboBox<>();
    private JPanel workPanel = new JPanel();

    public TasksPanel(ChronosGUI parent) {
        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5, 5, 5, 5);

        c.gridx = 0;
        c.gridy = 0;

        JLabel labelTasks = new JLabel("Tasks:");
        add(labelTasks, c);

        c.gridx++;
        c.weightx = 1;
        add(comboBoxTasks, c);

        comboBoxTasks.addActionListener((actionEvent) -> {
            workPanel.removeAll();

            Task selectedTask = getSelectedTask();
            if (selectedTask == null) {
                return;
            }

            workPanel.add(new WorkPanel(selectedTask.getWorkList()), BorderLayout.CENTER);
            parent.pack();
        });

        fillTasksCombo();

        JButton buttonAdd = new JButton("Activate");
        buttonAdd.addActionListener((actionEvent) -> {
            System.out.println("activate.");
        });

        c.gridx++;
        c.weightx = 0;
        add(buttonAdd, c);

        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 3;

        workPanel.setLayout(new BorderLayout());
        add(workPanel, c);
    }

    public Task getSelectedTask() {
        TaskComboBoxItem selectedItem = (TaskComboBoxItem) comboBoxTasks.getSelectedItem();
        Task task = selectedItem.getValue();

        return task;
    }

    private void fillTasksCombo() {
        try {
            comboBoxTasks.removeAllItems();
            List<Task> tasks = chronosService.listTasks();
            for (Task t : tasks) {
                comboBoxTasks.addItem(new TaskComboBoxItem(t));
            }
        } catch (ChronosCoreException e) {
            e.printStackTrace();
        }
    }
}

class TaskComboBoxItem {
    private String label;
    private Task value;

    public TaskComboBoxItem(Task value) {
        setLabel(value.getName());
        setValue(value);
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Task getValue() {
        return value;
    }

    public void setValue(Task value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return getLabel();
    }
}
