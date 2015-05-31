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
    private JButton buttonActivate = new JButton("Activate");
    private JPanel workPanel = new JPanel();

    public TasksPanel() {
        MessageBus.getInstance().register(this);
        setLayout(new GridBagLayout());
        workPanel.setLayout(new BorderLayout());

        createLayout();

        initHandlers();

        fillTasksCombo();
    }

    private void createLayout() {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5, 5, 5, 5);

        setConstraint(c, 0, 0, 3, 0);
        add(new ActiveTaskPanel(), c);

        setConstraint(c, 0, 1, 1, 0);
        add(new JLabel("Tasks:"), c);
        setConstraint(c, 1, 1, 1, 1);
        add(comboBoxTasks, c);
        setConstraint(c, 2, 1, 1, 0);
        add(buttonActivate, c);

        setConstraint(c, 0, 2, 3, 1);
        c.weighty = 1;
        add(workPanel, c);
    }

    private void setConstraint(GridBagConstraints c, int x, int y, int width, int weight) {
        c.gridx = x;
        c.gridy = y;
        c.gridwidth = width;
        c.gridwidth = width;
        c.weightx = weight;
    }

    private void initHandlers() {
        comboBoxTasks.addActionListener((actionEvent) -> {
            updateActivateButtonText();
            updateWorkPanel();
        });

        buttonActivate.addActionListener((actionEvent) -> {
            Task selectedTask = getSelectedTask();
            if (selectedTask == null) {
                return;
            }

            try {
                if (selectedTask.isActive()) {
                    chronosService.deactivateActiveTask();
                } else {
                    chronosService.activateTask(selectedTask.getId());
                }
                updateActivateButtonText();
            } catch (ChronosCoreException e) {
                e.printStackTrace();
            } catch (ChronosServiceException e) {
                e.printStackTrace();
            }

            updateWorkPanel();
            MessageBus.getInstance().sendMessage(ActiveTaskPanel.class, MessageType.RELOAD_DATA);
        });
    }

    private void updateWorkPanel() {
        workPanel.removeAll();

        Task selectedTask = getSelectedTask();
        if (selectedTask == null) {
            return;
        }

        workPanel.add(new WorkPanel(selectedTask.getId()), BorderLayout.CENTER);
        MessageBus.getInstance().sendMessage(ChronosGUI.class, MessageType.PACK);
    }

    private void updateActivateButtonText() {
        Task selectedTask = getSelectedTask();
        if (selectedTask == null) {
            return;
        }

        buttonActivate.setText(selectedTask.isActive() ? "Deactivate" : "Activate");
    }

    private Task getSelectedTask() {
        Object selection = comboBoxTasks.getSelectedItem();
        if (selection == null) {
            return null;
        }

        TaskComboBoxItem selectedItem = (TaskComboBoxItem) selection;
        Task task = null;
        try {
            task = chronosService.getTask(selectedItem.getTaskId());

            return task;
        } catch (ChronosCoreException e) {
            e.printStackTrace();
        }

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
    private Long taskId;

    public TaskComboBoxItem(Task value) {
        setLabel(value.getName());
        taskId = value.getId();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    @Override
    public String toString() {
        return getLabel();
    }
}
