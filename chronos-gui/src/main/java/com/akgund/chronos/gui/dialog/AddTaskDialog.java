package com.akgund.chronos.gui.dialog;

import com.akgund.chronos.ChronosServiceFactory;
import com.akgund.chronos.core.impl.ChronosCoreException;
import com.akgund.chronos.model.Task;
import com.akgund.chronos.service.IChronosService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;

public class AddTaskDialog extends JDialog {
    private static final Logger logger = LogManager.getLogger(AddTaskDialog.class);

    private IChronosService chronosService = ChronosServiceFactory.create();
    private JTextField textFieldName = new JTextField(20);

    public AddTaskDialog() {
        setModal(true);
        setTitle("Add new task");

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(5, 5, 5, 5);

        JLabel labelTaskName = new JLabel("Task Name:");
        contentPane.add(labelTaskName, c);


        c.weightx = 1;
        c.gridx++;
        contentPane.add(textFieldName, c);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener((actionEvent) -> {
            String taskName = textFieldName.getText();
            if (taskName == null || taskName.trim().isEmpty()) {
                return;
            }

            Task task = new Task();
            task.setName(taskName.trim());
            try {
                chronosService.saveTask(task);
                AddTaskDialog.this.dispose();
            } catch (ChronosCoreException e) {
                logger.error(e.getMessage(), e);
            }
        });

        c.gridx = 1;
        c.gridy = 1;
        c.weightx = 0;
        contentPane.add(saveButton, c);

        setContentPane(contentPane);
        pack();
    }
}
