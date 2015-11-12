package com.akgund.chronos.gui.panel;

import com.akgund.chronos.ChronosServiceFactory;
import com.akgund.chronos.core.impl.ChronosCoreException;
import com.akgund.chronos.gui.bus.IMessageClient;
import com.akgund.chronos.gui.bus.MessageBus;
import com.akgund.chronos.gui.bus.MessageType;
import com.akgund.chronos.gui.event.ITaskSelectionEvent;
import com.akgund.chronos.gui.widget.AbstractComboBoxItem;
import com.akgund.chronos.model.Task;
import com.akgund.chronos.service.IChronosService;

import javax.swing.*;
import java.util.List;

public class TaskSelectionPanel extends JPanel implements IMessageClient {
    private IChronosService chronosService = ChronosServiceFactory.create();
    private JComboBox<TaskComboBoxItem> comboBoxTasks = new JComboBox<>();
    private ITaskSelectionEvent taskSelectionEvent;

    public TaskSelectionPanel() {
        this(null);
    }

    public TaskSelectionPanel(ITaskSelectionEvent event) {
        MessageBus.getInstance().register(this);
        setTaskSelectionEvent(event);

        comboBoxTasks.addActionListener((actionEvent) -> {
            TaskComboBoxItem selectedItem = (TaskComboBoxItem) comboBoxTasks.getSelectedItem();
            if (selectedItem == null) {
                return;
            }

            getTaskSelectionEvent().onSelected(getSelectedTask());
        });

        createLayout();

        fillTasksCombo();
    }

    private void createLayout() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        add(new JLabel("Tasks:"));
        add(comboBoxTasks);
    }

    public ITaskSelectionEvent getTaskSelectionEvent() {
        if (taskSelectionEvent == null) {
            /* Do nothing. */
            taskSelectionEvent = taskId -> {
            };
        }

        return taskSelectionEvent;
    }

    public void setTaskSelectionEvent(ITaskSelectionEvent taskSelectionEvent) {
        this.taskSelectionEvent = taskSelectionEvent;
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

    public Task getSelectedTask() {
        Object selection = comboBoxTasks.getSelectedItem();
        if (selection == null) {
            return null;
        }

        TaskComboBoxItem selectedItem = (TaskComboBoxItem) selection;
        Task task = null;
        try {
            task = chronosService.getTask(selectedItem.getValue().getId());

            return task;
        } catch (ChronosCoreException e) {
            e.printStackTrace();
        }

        return task;
    }

    @Override
    public void receiveMessage(MessageType message) {
        if (MessageType.RELOAD_DATA == message) {
            fillTasksCombo();
        }
    }
}

class TaskComboBoxItem extends AbstractComboBoxItem<Task> {

    public TaskComboBoxItem(Task value) {
        super(value);
    }

    @Override
    public String getLabel() {
        if (getValue() == null) {
            return "error";
        }

        return getValue().getName();
    }
}
