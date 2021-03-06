package com.akgund.chronos.gui.panel;

import com.akgund.chronos.ChronosServiceFactory;
import com.akgund.chronos.core.impl.ChronosCoreException;
import com.akgund.chronos.gui.ChronosGUI;
import com.akgund.chronos.gui.bus.MessageBus;
import com.akgund.chronos.gui.bus.MessageType;
import com.akgund.chronos.gui.dialog.AddWorkDialog;
import com.akgund.chronos.model.Task;
import com.akgund.chronos.service.IChronosService;
import com.akgund.chronos.service.impl.ChronosServiceException;
import com.akgund.chronos.util.GUIUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;

public class TasksPanel extends JPanel {
    private static final Logger logger = LogManager.getLogger(TasksPanel.class);
    private IChronosService chronosService = ChronosServiceFactory.create();
    private JButton buttonActivate = new JButton("Activate");
    private JButton buttonAddWork = new JButton("Add Work");
    private JPanel workPanel = new JPanel();
    private TaskSelectionPanel taskSelectionPanel;

    public TasksPanel() {
        setLayout(new GridBagLayout());

        createLayout();

        initHandlers();
    }

    private void createLayout() {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5, 5, 5, 5);

        GUIUtil.setConstraint(c, 0, 0, 3, 0);
        add(new ActiveTaskPanel(), c);

        GUIUtil.setConstraint(c, 0, 1, 2, 1);
        taskSelectionPanel = new TaskSelectionPanel(task -> {
            updateWorkPanel(task);
            updateActivateButtonText();
        });
        add(taskSelectionPanel, c);

        GUIUtil.setConstraint(c, 2, 1, 1, 0);
        add(buttonActivate, c);
        GUIUtil.setConstraint(c, 3, 1, 1, 0);
        add(buttonAddWork, c);

        GUIUtil.setConstraint(c, 0, 2, 4, 1);
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        JScrollPane workWrapper = new JScrollPane(workPanel);
        add(workWrapper, c);

        updateActivateButtonText();
    }

    private void initHandlers() {
        buttonActivate.addActionListener((actionEvent) -> {
            Task selectedTask = taskSelectionPanel.getSelectedTask();
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
                logger.error(e.getMessage(), e);
            } catch (ChronosServiceException e) {
                logger.error(e.getMessage(), e);
            }

            updateWorkPanel(taskSelectionPanel.getSelectedTask());
            MessageBus.getInstance().sendMessage(ActiveTaskPanel.class, MessageType.RELOAD_DATA);
        });

        buttonAddWork.addActionListener(e -> {
            Task selectedTask = taskSelectionPanel.getSelectedTask();
            if (selectedTask == null) {
                return;
            }

            AddWorkDialog addWorkDialog = new AddWorkDialog(selectedTask.getId());
            addWorkDialog.setLocationRelativeTo(this);
            addWorkDialog.setVisible(true);

            updateWorkPanel(taskSelectionPanel.getSelectedTask());
            MessageBus.getInstance().sendMessage(ActiveTaskPanel.class, MessageType.RELOAD_DATA);
        });
    }

    private void updateWorkPanel(Task task) {
        logger.debug("Updating work panel.");
        workPanel.removeAll();

        if (task == null) {
            return;
        }

        logger.debug(String.format("Will update work panel for task: '%s'", task.getName()));
        workPanel.add(new WorkPanel(task.getId()));
        MessageBus.getInstance().sendMessage(ChronosGUI.class, MessageType.PACK);
    }

    private void updateActivateButtonText() {
        if (taskSelectionPanel == null) {
            return;
        }

        Task selectedTask = taskSelectionPanel.getSelectedTask();
        if (selectedTask == null) {
            return;
        }

        logger.debug(String.format("Will update active button text for task: '%s'", selectedTask.getName()));
        buttonActivate.setText(selectedTask.isActive() ? "Deactivate" : "Activate");
    }
}