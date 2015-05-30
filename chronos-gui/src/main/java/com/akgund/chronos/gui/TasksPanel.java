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

    public TasksPanel() {
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

        fillTasksCombo();

        JButton buttonAdd = new JButton("+");
        buttonAdd.addActionListener((actionEvent) -> {
            AddTaskDialog addTaskDialog = new AddTaskDialog();
            addTaskDialog.setVisible(true);

            fillTasksCombo();
        });

        c.gridx++;
        c.weightx = 0;
        add(buttonAdd, c);
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
