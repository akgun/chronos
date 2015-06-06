package com.akgund.chronos.gui;

import com.akgund.chronos.ChronosServiceFactory;
import com.akgund.chronos.service.IChronosService;

import javax.swing.*;
import java.awt.*;

public class AddWorkDialog extends JDialog {
    private IChronosService chronosService = ChronosServiceFactory.create();
    private Long taskId;

    public AddWorkDialog(Long taskId) {
        this.taskId = taskId;
        setTitle("Add Work");

        setContentPane(createLayout());
    }

    private JPanel createLayout() {
        JPanel panel = new JPanel();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5, 5, 5, 5);

        return panel;
    }
}
