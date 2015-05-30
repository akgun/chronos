package com.akgund.chronos.gui;

import com.akgund.chronos.core.ChronosCoreException;
import com.akgund.chronos.gui.bus.IMessageClient;
import com.akgund.chronos.gui.bus.MessageBus;
import com.akgund.chronos.gui.bus.MessageType;
import com.akgund.chronos.model.Task;
import com.akgund.chronos.service.ChronosServiceException;
import com.akgund.chronos.service.IChronosService;
import com.akgund.chronos.util.CDIFactory;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TasksPanel extends JPanel implements IMessageClient {
    private IChronosService chronosService = CDIFactory.getInstance().inject(IChronosService.class);
    private JComboBox<TaskComboBoxItem> comboBoxTasks = new JComboBox<>();
    private JPanel workPanel = new JPanel();

    public TasksPanel() {
        MessageBus.getInstance().register(this);

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
            updateWorkPanel();
        });

        fillTasksCombo();

        JButton buttonActivate = new JButton("Activate");
        buttonActivate.addActionListener((actionEvent) -> {
            Task selectedTask = getSelectedTask();
            if (selectedTask == null) {
                return;
            }

            try {
                chronosService.activateTask(selectedTask.getId());
            } catch (ChronosCoreException e) {
                e.printStackTrace();
            } catch (ChronosServiceException e) {
                e.printStackTrace();
            }

            updateWorkPanel();
        });

        c.gridx++;
        c.weightx = 0;
        add(buttonActivate, c);

        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 3;

        workPanel.setLayout(new BorderLayout());
        add(workPanel, c);
    }

    private void updateWorkPanel() {
        workPanel.removeAll();

        Task selectedTask = getSelectedTask();
        if (selectedTask == null) {
            return;
        }

        workPanel.add(new WorkPanel(selectedTask.getWorkList()), BorderLayout.CENTER);
        MessageBus.getInstance().sendMessage(ChronosGUI.class, MessageType.PACK);
    }

    public Task getSelectedTask() {
        Object selection = comboBoxTasks.getSelectedItem();
        if (selection == null) {
            return null;
        }

        TaskComboBoxItem selectedItem = (TaskComboBoxItem) selection;
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

            if (tasks != null && !tasks.isEmpty()) {
                comboBoxTasks.setSelectedItem(tasks.stream().findFirst().get());
            }
        } catch (ChronosCoreException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void receiveMessage(MessageType message) {
        if (MessageType.RELOAD_DATA == message) {
            fillTasksCombo();
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
