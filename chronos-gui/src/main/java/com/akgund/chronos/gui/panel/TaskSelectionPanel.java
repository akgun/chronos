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
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.util.List;

public class TaskSelectionPanel extends JPanel implements IMessageClient {
    private static final Logger logger = LogManager.getLogger(TaskSelectionPanel.class);
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
                logger.debug("No selected item for tasks combo.");
                return;
            }

            final Task selectedTask = selectedItem.getValue();

            logger.debug(String.format("Propagating selected event for task: '%s'",
                    selectedTask.getName()));
            getTaskSelectionEvent().onSelected(selectedTask);
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
            logger.debug("No task selection event provided.");
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
        logger.debug("Filling tasks combo.");

        try {
            comboBoxTasks.removeAllItems();
            final List<Task> tasks = chronosService.listTasks();
            for (Task t : tasks) {
                comboBoxTasks.addItem(new TaskComboBoxItem(t));
            }

            final Task activeTask = chronosService.findActiveTask();
            if (activeTask != null) {
                logger.debug(String.format("Setting selected task: '%s'", activeTask.getName()));

                setSelectedTask(activeTask);
            } else {
                logger.debug("No active task found.");
            }
        } catch (ChronosCoreException e) {
            logger.error(e.getMessage(), e);
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
            logger.debug(String.format("Getting selected task: '%s'", task.getName()));

            return task;
        } catch (ChronosCoreException e) {
            logger.error(e.getMessage(), e);
        }

        return task;
    }

    public void setSelectedTask(Task task) {
        for (int i = 0; i < comboBoxTasks.getItemCount(); i++) {
            if (task.getName().equals(comboBoxTasks.getItemAt(i).getValue().getName())) {
                comboBoxTasks.setSelectedIndex(i);
                break;
            }
        }
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
